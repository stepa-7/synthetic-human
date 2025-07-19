package com.stepa7.starter.command;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandQueueExecutor {
    private final BlockingQueue<Command> commandsQueue;
    private final ThreadPoolExecutor threadPoolExecutor;

    public void executeCommand(Command command) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(command.toString() + " is completed");
    }

    @PostConstruct
    public void init() {
        Thread dispatcher = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Command command = commandsQueue.take();
                    threadPoolExecutor.execute(() -> {
                        try {
                            executeCommand(command);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dispatcher.setDaemon(true);
        dispatcher.start();
    }
}
