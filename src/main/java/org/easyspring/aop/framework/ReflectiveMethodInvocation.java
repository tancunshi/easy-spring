package org.easyspring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.test.aop.Controller;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object targetObject;
    protected final Method targetMethod;
    protected final Object[] arguments;

    protected final List<MethodInterceptor> interceptors;
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod,
                                      Object[] arguments, List<MethodInterceptor> interceptors) {
        this.targetObject = targetObject;
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
        //调用织入点方法
        return this.targetMethod.invoke(this.targetObject,this.arguments);
    }

    public Object getThis() {
        return this.targetObject;
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
