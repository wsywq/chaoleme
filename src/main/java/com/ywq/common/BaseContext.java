package com.ywq.common;

public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        return threadLocal.get();
    }

    public static void setCurrentUserId(Long id) {
        threadLocal.set(id);
    }
}
