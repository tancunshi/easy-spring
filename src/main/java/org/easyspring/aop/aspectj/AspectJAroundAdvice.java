package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

public class AspectJAroundAdvice extends AbstractAspectJAdvice{

    public AspectJAroundAdvice(Method adviceMethod, AspectInstanceFactory adviceObjectFactory,
                               AspectJExpressionPointcut pointcut) {
        super(adviceMethod, adviceObjectFactory, pointcut);
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return this.invokeAdviceMethod(new Object[]{methodInvocation});
    }
}
