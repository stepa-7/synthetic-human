package com.stepa7.starter.command;

import com.stepa7.starter.config.SyntheticHumanCoreAutoConfiguration;
import com.stepa7.starter.exception.CommandQueueOverflowException;
import com.stepa7.starter.metrics.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
@RequiredArgsConstructor
public class CommandSender {
    private final BlockingQueue<Command> commandsQueue;
    private final CommandQueueExecutor queueExecutor;
    private final MetricsService metricsService;

    public void sendCommand(Command command) throws InterruptedException {
        if (command.getPriority().equals(Priority.COMMON)) {
            if (commandsQueue.size() >= SyntheticHumanCoreAutoConfiguration.MAX_QUEUE_SIZE) {
                throw new CommandQueueOverflowException("Command queue is full");
            } else {
                commandsQueue.add(command);
                metricsService.updateQueueSize(commandsQueue.size());
            }
        } else {
            queueExecutor.executeImmediate(command);
        }
    }
}
