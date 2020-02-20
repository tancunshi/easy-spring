package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import java.lang.reflect.Method;

/**
 * after和after-returning的区别在于after无论是否异常都会执行
 * @author tancunshi
 */
public class AspectJAfterAdvice extends AbstractAspectJAdvice{

    public AspectJAfterAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {
        super(adviceMethod, adviceObject, pointcut);
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        }
        finally {
            this.invokeAdviceMethod();
        }
    }
}
