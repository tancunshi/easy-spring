package org.easyspring.aop;

import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
public interface MethodMatcher {
    boolean matches(Method method);
}
