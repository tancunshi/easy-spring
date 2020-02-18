package org.easyspring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * Advice是织入pointcut的策略，而织入的内容 = adviceObject + adviceMethod
 */
public interface Advice extends MethodInterceptor {
    Pointcut getPointcut();
}
