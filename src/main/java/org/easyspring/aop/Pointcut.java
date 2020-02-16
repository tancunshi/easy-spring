package org.easyspring.aop;

/**
 * @author tancunshi
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
    String getExpression();
}
