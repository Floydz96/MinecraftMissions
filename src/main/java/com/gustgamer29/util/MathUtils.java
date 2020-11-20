package com.gustgamer29.util;

public class MathUtils {

    public static double round(double v, int places) {
        long factor = (long) Math.pow(10, places);
        v = v * factor;
        long tmp = Math.round(v);
        return (double) tmp / factor;
    }

}
