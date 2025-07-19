package com.stepa7.starter.command;

import com.stepa7.starter.android.Android;
import com.stepa7.starter.android.AndroidService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
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

    public void executeCommand(Android android, Command command) {
        System.out.println("Android" + android.getName() + "starts " + command.toString());
        android.setBusy(true);

        try {
            Thread.sleep(5000);
            System.out.println(command.toString() + " is completed");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            android.setBusy(false);
            System.out.println("Android" + android.getName() + "completed " + command.toString());
        }

    }

    public void executeImmediate(Command command) throws InterruptedException {
        Optional<Android> optionalAndroid;
        // Wait for available android
        while ((optionalAndroid = androidService.getAvailableAndroid()).isEmpty()) {
            Thread.sleep(300);
        }
        Android android = optionalAndroid.get();
        threadPoolExecutor.execute(() -> executeCommand(android, command));
    }

    @PostConstruct
    public void init() {
        Thread dispatcher = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Command command = commandsQueue.take();
                    Optional<Android> optionalAndroid;
                    // Wait for available android
                    while ((optionalAndroid = androidService.getAvailableAndroid()).isEmpty()) {
                        Thread.sleep(300);
                    }
                    Android android = optionalAndroid.get();
                    threadPoolExecutor.execute(() -> executeCommand(android, command));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dispatcher.setDaemon(true);
        dispatcher.start();
    }
}
