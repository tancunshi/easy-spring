package org.easyspring.aop;

public interface Pointcut {
    MethodMatcher getMethodMatcher();
    String getExpression();
}
