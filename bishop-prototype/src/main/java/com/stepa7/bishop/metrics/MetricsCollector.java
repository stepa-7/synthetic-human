package com.stepa7.bishop.metrics;

import com.stepa7.starter.command.Command;
import com.stepa7.starter.metrics.MetricsService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetricsCollector implements MetricsService {
    private final MeterRegistry meterRegistry;
    private final AtomicInteger queueSize = new AtomicInteger(0);

    public MetricsCollector(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        meterRegistry.gauge("bishop.android.queue.size", queueSize);
    }

    @Override
    public void recordCommandExecuted(Command command) {
        meterRegistry.counter("bishop.commands.executed", "author", command.getAuthor()).increment();
    }

    @Override
    public void updateQueueSize(int size) {
        queueSize.set(size);
    }
}
