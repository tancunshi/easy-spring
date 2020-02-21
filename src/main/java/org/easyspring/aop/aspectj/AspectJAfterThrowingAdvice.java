package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {
    public AspectJAfterThrowingAdvice(Method adviceMethod, AspectInstanceFactory adviceObjectFactory,
                                      AspectJExpressionPointcut pointcut) {
        super(adviceMethod, adviceObjectFactory, pointcut);
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
