package org.easyspring.util;

import java.io.FileNotFoundException;

public class Assert {
    public static void notNull(Object path, String msg) {
        if (path == null){
            throw new IllegalArgumentException(msg);
        }
    }
}
