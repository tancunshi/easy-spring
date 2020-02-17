package org.easyspring.aop.aspectj;

import org.easyspring.aop.Advice;
import org.easyspring.aop.Pointcut;
import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {

    protected AspectJExpressionPointcut pointcut;
    protected Method adviceMethod;
    protected Object adviceObject;

    public AbstractAspectJAdvice(Method adviceMethod,Object adviceObject,AspectJExpressionPointcut pointcut){
        this.adviceMethod = adviceMethod;
        this.adviceObject = adviceObject;
        this.pointcut = pointcut;
    }

    public void invokeAdviceMethod() throws Throwable{
        adviceMethod.invoke(adviceObject);
    }

    public Pointcut getPointcut(){
        return this.pointcut;
    }

    public Method getAdviceMethod(){
        return this.adviceMethod;
    }
}
