package com.gustgamer29.database.internal.dao;

import java.util.List;

public interface DAO<I, V>{

    V getById(I identifier);
    List<V> getAll();

    void save(V obj);
    void delete(I identifier);

    void update(I identifier, V obj);
}
