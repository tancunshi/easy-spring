package org.easyspring.util;

/**
 * @author spring source code
 */
public class Assert {
    public static void notNull(Object path, String msg) {
        if (path == null){
            throw new IllegalArgumentException(msg);
        }
    }
}
