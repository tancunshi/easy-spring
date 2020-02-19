package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {
    public AspectJAfterReturningAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {
        super(adviceMethod,adviceObject,pointcut);
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object object = methodInvocation.proceed();
        this.invokeAdviceMethod();
        return object;
    }
}
