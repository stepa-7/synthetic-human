package com.stepa7.starter.command;

import com.stepa7.starter.android.Android;
import com.stepa7.starter.android.AndroidService;
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

    public void executeImmediate(Command command) throws InterruptedException {
        metricsService.updateQueueSize(commandsQueue.size());
        Optional<Android> optionalAndroid;
        // Wait for available android
        while ((optionalAndroid = androidService.getAvailableAndroid()).isEmpty()) {
            Thread.sleep(300);
        }
        Android android = optionalAndroid.get();
        threadPoolExecutor.execute(() -> commandExecutionService.executeCommand(android, command));
    }

    @PostConstruct
    public void init() {
        Thread dispatcher = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    metricsService.updateQueueSize(commandsQueue.size());
                    Command command = commandsQueue.take();
                    metricsService.updateQueueSize(commandsQueue.size()); // Decreased

                    Optional<Android> optionalAndroid;
                    // Wait for available android
                    while ((optionalAndroid = androidService.getAvailableAndroid()).isEmpty()) {
                        Thread.sleep(300);
                    }
                    Android android = optionalAndroid.get();
                    threadPoolExecutor.execute(() -> commandExecutionService.executeCommand(android, command));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dispatcher.setDaemon(true);
        dispatcher.start();
    }
}
