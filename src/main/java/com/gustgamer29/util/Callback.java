package com.gustgamer29.util;

public interface Callback<E> {

    void call(E response, Throwable error);
}
