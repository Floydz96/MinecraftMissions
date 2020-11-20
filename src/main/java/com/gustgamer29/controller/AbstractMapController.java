package com.gustgamer29.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public abstract class AbstractMapController<K, V> implements Controller<K, V> {

    @Getter(AccessLevel.PROTECTED)
    private Map<K, V> controllerMap;

    protected AbstractMapController() {
        this.controllerMap = Maps.newHashMap();
    }

    @Override
    public void register(K toRegister, V value) {
        this.controllerMap.computeIfAbsent(toRegister, player1 -> value);
    }

    @Override
    public void unregister(K unregisterTarget) {
        this.controllerMap.remove(unregisterTarget);
    }

    @Override
    public boolean contains(K target) {
        return controllerMap.keySet().stream().anyMatch(k -> contains().compare(k, target));
    }

    protected abstract Comparator<K> contains();

    @Override
    public Map<K, V> all() {
        return ImmutableMap.copyOf(this.controllerMap);
    }

    @Override
    public List<K> keys() {
        return ImmutableList.copyOf(this.controllerMap.keySet());
    }

    @Override
    public List<V> values() {
        return ImmutableList.copyOf(this.controllerMap.values());
    }

    @Override
    public V fromKey(K target) {
        return this.controllerMap.entrySet()
                .stream()
                .filter(kvEntry -> contains().compare(kvEntry.getKey(), target))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getValue();
    }

}
