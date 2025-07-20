package com.stepa7.starter.command;

import com.stepa7.starter.android.Android;
import com.stepa7.starter.android.AndroidService;
import com.stepa7.starter.exception.CommandQueueOverflowException;
import com.stepa7.starter.exception.NoAvailableAndroidException;
import com.stepa7.starter.metrics.MetricsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandQueueExecutor {
    private final BlockingQueue<Command> commandsQueue;
    private final AndroidService androidService;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final CommandExecutionService commandExecutionService;
    private final MetricsService metricsService;

    private static final long TIMEOUT_MILLIS = 3000;

    public void executeImmediate(Command command) throws InterruptedException {
        metricsService.updateQueueSize(commandsQueue.size());
        Android android = waitForAvailableAndroid();
        threadPoolExecutor.execute(() -> commandExecutionService.executeCommand(android, command));
    }

    private Android waitForAvailableAndroid() throws InterruptedException {
        long start = System.currentTimeMillis();
        Optional<Android> optionalAndroid;
        // Wait for available android
        while ((optionalAndroid = androidService.getAvailableAndroid()).isEmpty()) {
            if (System.currentTimeMillis() - start >= TIMEOUT_MILLIS) {
                throw new NoAvailableAndroidException("No available androids to execute command");
            }
            Thread.sleep(300);
        }
        return optionalAndroid.get();
    }

    @PostConstruct
    public void init() {
        Thread dispatcher = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    metricsService.updateQueueSize(commandsQueue.size());
                    Command command = commandsQueue.take();
                    metricsService.updateQueueSize(commandsQueue.size()); // Decreased

                    Android android;
                    try {
                        android = waitForAvailableAndroid();
                    } catch (NoAvailableAndroidException e) {
                        log.warn("No available androids to execute command, returning command to queue");
                        commandsQueue.put(command);
                        Thread.sleep(1000);
                        continue;
                    }

                    threadPoolExecutor.execute(() -> commandExecutionService.executeCommand(android, command));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.warn("Dispatcher thread interrupted", e);
                } catch (NoAvailableAndroidException ex) {
                    log.error("No available androids for command execution", ex);
                }
            }
        });
        dispatcher.setDaemon(true);
        dispatcher.start();
    }
}
