package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AspectJAroundAdvice extends AbstractAspectJAdvice{

    public AspectJAroundAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {
        super(adviceMethod, adviceObject, pointcut);
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return this.invokeAdviceMethod(new Object[]{methodInvocation});
    }
}
