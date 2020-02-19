package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
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
