package org.easyspring.util;

import java.lang.reflect.Method;

public final class BeanUtils {
    public static Method resolveSignature(String methodName, Class<?> beanClass) {
        if (!StringUtils.hasText(methodName)){
            throw new IllegalArgumentException("Property 'methodName' not length");
        }

        try {
            return beanClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("resolve method " + methodName +"of class " + beanClass.getName() + " fail",e);
        }
    }
}
