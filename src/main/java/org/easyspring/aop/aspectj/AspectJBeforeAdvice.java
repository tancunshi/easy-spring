package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.tx.TransactionManager;

import java.lang.reflect.Method;

public class AspectJBeforeAdvice extends AbstractAspectJAdvice {
    public AspectJBeforeAdvice(Method adviceMethod,Object adviceObject, AspectJExpressionPointcut pointcut) {
        super(adviceMethod, adviceObject, pointcut);
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.invokeAdviceMethod();
        return methodInvocation.proceed();
    }
}
