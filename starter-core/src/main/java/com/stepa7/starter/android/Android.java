package com.stepa7.starter.android;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.concurrent.atomic.AtomicBoolean;

@Data
public class Android {
    private Long id;
    @NotBlank
    private String name;
    private final AtomicBoolean busy = new AtomicBoolean(false);

    public boolean isBusy() {
        return busy.get();
    }

    public boolean tryLock() {
        return busy.compareAndSet(false, true);
    }

    public void release() {
        busy.set(false);
    }
}
