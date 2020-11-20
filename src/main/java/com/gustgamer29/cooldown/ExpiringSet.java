package com.gustgamer29.cooldown;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ExpiringSet<E> {

    private Map<E, Long> entries = Maps.newConcurrentMap();
    private long expirationMillis;

    public ExpiringSet(long duration, TimeUnit unit) {
        setExpiration(duration, unit);
    }

    public void add(E entry) {
        entries.put(entry, System.currentTimeMillis() + expirationMillis);
    }

    public boolean contains(E entry) {
        Long expiration = entries.get(entry);
        if (expiration == null) {
            return false;
        } else if (expiration > System.currentTimeMillis()) {
            return true;
        } else  {
            entries.remove(entry);
            return false;
        }

    }

    public void remove(E entry) {
        entries.remove(entry);
    }

    public void clear() {
        entries.clear();
    }

    public void removeExpiredEntries() {
        entries.entrySet().removeIf(entry -> System.currentTimeMillis() > entry.getValue());
    }

    public Duration getExpiration(E entry) {
        Long expiration = entries.get(entry);
        if (expiration == null) {
            return new Duration(-1, TimeUnit.SECONDS);
        }
        long stillPresentMillis = expiration - System.currentTimeMillis();
        if (stillPresentMillis <= 0) {
            entries.remove(entry);
            return new Duration(-1, TimeUnit.SECONDS);
        }
        return Duration.createWithSuitableUnit(stillPresentMillis, TimeUnit.MILLISECONDS);
    }

    public void setExpiration(long duration, TimeUnit unit) {
        this.expirationMillis = unit.toMillis(duration);
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }
}
