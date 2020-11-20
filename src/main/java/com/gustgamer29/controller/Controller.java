package com.gustgamer29.controller;

import java.util.List;
import java.util.Map;

public interface Controller<K, V> {

    void register(K toRegister, V value);

    void unregister(K unregisterTarget);

    boolean contains(K target);

    V fromKey(K target);

    Map<K, V> all();

    List<K> keys();

    List<V> values();

    interface Comparator<K> {
        boolean compare(K c, K c2);

    }
}
