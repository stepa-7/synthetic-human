package com.stepa7.starter.command;

import com.stepa7.starter.config.SyntheticHumanCoreAutoConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
@RequiredArgsConstructor
public class CommandSender {
    private final BlockingQueue<Command> commandsQueue;
    private final CommandQueueExecutor queueExecutor;

    public void sendCommand(Command command) throws InterruptedException {
        if (command.getPriority().equals(Priority.COMMON)) {
            if (commandsQueue.size() >= SyntheticHumanCoreAutoConfiguration.MAX_QUEUE_SIZE) {
                throw new StackOverflowError("Command queue is full");
            } else {
                commandsQueue.add(command);
            }
        } else {
            queueExecutor.executeImmediate(command);
        }
    }
}
