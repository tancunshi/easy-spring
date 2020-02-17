package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.tx.TransactionManager;

import java.lang.reflect.Method;

public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {
    public AspectJAfterThrowingAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {
        super(adviceMethod,adviceObject,pointcut);
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        }
        catch (Throwable t){
            this.invokeAdviceMethod();
            throw t;
        }
    }
}
