package org.easyspring.util;

public class Assert {
    public static void notNull(Object path, String msg) {
        if (path == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void arraySizeGreaterZero(int size, String msg){
        if (size < 0){
            throw new IllegalArgumentException(msg);
        }
    }
}
