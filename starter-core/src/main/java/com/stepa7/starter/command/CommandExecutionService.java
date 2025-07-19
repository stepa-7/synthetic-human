package com.stepa7.starter.command;

import com.stepa7.starter.android.Android;
import com.stepa7.starter.audit.WeylandWatchingYou;
import com.stepa7.starter.metrics.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandExecutionService {
    private final MetricsService metricsService;

    @WeylandWatchingYou
    public void executeCommand(Android android, Command command) {
        android.setBusy(true);
        try {
            Thread.sleep(5000);
            metricsService.recordCommandExecuted(command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            android.setBusy(false);
        }
    }
}
