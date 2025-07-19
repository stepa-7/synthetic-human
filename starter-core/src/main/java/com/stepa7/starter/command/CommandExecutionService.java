package com.stepa7.starter.command;

import com.stepa7.starter.android.Android;
import com.stepa7.starter.audit.WeylandWatchingYou;
import org.springframework.stereotype.Service;

@Service
public class CommandExecutionService {
    @WeylandWatchingYou
    public void executeCommand(Android android, Command command) {
        android.setBusy(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            android.setBusy(false);
        }

    }
}
