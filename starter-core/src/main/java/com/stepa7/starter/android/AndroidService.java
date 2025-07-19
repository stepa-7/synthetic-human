package com.stepa7.starter.android;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AndroidService {
    private final ConcurrentHashMap<Long, Android> storage = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    public Android registerAndroid(Android android) {
        android.setId(id.getAndIncrement());
        storage.put(android.getId(), android);
        return android;
    }

    public Optional<Android> getAvailableAndroid() {
        return storage
                .values()
                .stream().
                filter(a -> !a.isBusy())
                .findFirst();
    }

    public ConcurrentHashMap<Long, Android> getAndroids() {
        return storage;
    }

    public long getBusyCount() {
        return storage
                .values()
                .stream()
                .filter(Android::isBusy)
                .count();
    }
}
