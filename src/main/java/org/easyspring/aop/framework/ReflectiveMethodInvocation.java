package org.easyspring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 一个JoinPoint = targetObject + targetMethod
 * 一个ReflectiveMethodInvocation用于存放一个JoinPoint的所有Advice，并依次调用Advice
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object object;
    protected final Method targetMethod;
    protected final Object[] arguments;

    protected final List<MethodInterceptor> interceptors;
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object object, Method targetMethod,
                                      Object[] arguments, List<MethodInterceptor> interceptors) {
        this.object = object;
        this.targetMethod = targetMethod;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }

    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptors.size() - 1){
            return this.invokeJoinPoint();
        }
        this.currentInterceptorIndex ++;
        MethodInterceptor interceptor = this.interceptors.get(this.currentInterceptorIndex);
        return interceptor.invoke(this);
    }

    protected Object invokeJoinPoint() throws Throwable{
        //调用JoinPoint方法
        return this.targetMethod.invoke(this.object,this.arguments);
    }

    public Object getThis() {
        return this.object;
    }

    public AccessibleObject getStaticPart() {
        return this.targetMethod;
    }

    public Method getMethod() {
        return this.targetMethod;
    }

    public Object[] getArguments() {
        //不能传递null
        return (this.arguments != null ? this.arguments : new Object[0]);
    }
}
