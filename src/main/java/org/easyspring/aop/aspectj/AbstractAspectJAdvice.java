package org.easyspring.aop.aspectj;

import org.easyspring.aop.Advice;
import org.easyspring.aop.Pointcut;
import org.easyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * Advice是Aspect织入JoinPoint的策略，分别是在方法前织入，方法后织入，方法抛出异常时织入
 * @author tancunshi
 */
public abstract class AbstractAspectJAdvice implements Advice {

    protected Method adviceMethod;
    protected Object adviceObject;
    protected AspectJExpressionPointcut pointcut;

    public AbstractAspectJAdvice(Method adviceMethod, AspectInstanceFactory adviceObjectFactory,
                                 AspectJExpressionPointcut pointcut){
        this.adviceMethod = adviceMethod;
        this.adviceObject = adviceObjectFactory.getAspectInstance();
        this.pointcut = pointcut;
    }

    public void invokeAdviceMethod() throws Throwable{
        adviceMethod.invoke(adviceObject);
    }

    public Object invokeAdviceMethod(Object[] args) throws Throwable{
        return adviceMethod.invoke(adviceObject,args);
    }

    public Pointcut getPointcut(){
        return this.pointcut;
    }

    public Method getAdviceMethod(){
        return this.adviceMethod;
    }

    public Object getAdviceObject() {
        return adviceObject;
    }
}
