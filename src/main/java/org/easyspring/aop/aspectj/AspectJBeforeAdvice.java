package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {
    public AspectJBeforeAdvice(Method adviceMethod, AspectInstanceFactory adviceObjectFactory,
                               AspectJExpressionPointcut pointcut) {
        super(adviceMethod, adviceObjectFactory, pointcut);
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.invokeAdviceMethod();
        return methodInvocation.proceed();
    }
}
