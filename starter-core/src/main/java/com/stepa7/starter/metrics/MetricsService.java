package com.stepa7.starter.metrics;

import com.stepa7.starter.command.Command;

public interface MetricsService {
    void recordCommandExecuted(Command command);
    void updateQueueSize(int size);
}
