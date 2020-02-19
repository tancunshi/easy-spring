package org.easyspring.aop.framework;

import org.easyspring.aop.Advice;
import org.easyspring.aop.Pointcut;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个AopConfig对应一个target
 * @author tancunshi 
 */
public class AopConfigSupport implements AopConfig{

    private boolean proxyTargetClass = false;
    private Object targetObject = null;
    private List<Advice> advices = new ArrayList<Advice>();
    private List<Class> interfaces = new ArrayList<Class>();

    public void addAdvice(Advice advice) {
        this.advices.add(advice);
    }

    public List<Advice> getAdvices() {
        return this.advices;
    }

    public List<Advice> getAdvice(Method method) {
        List<Advice> result = new ArrayList<Advice>();
        for (Advice advice: advices){
            Pointcut pointcut = advice.getPointcut();
            if (pointcut.getMethodMatcher().matches(method)){
                result.add(advice);
            }
        }
        return result;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass){
        this.proxyTargetClass = proxyTargetClass;
    }

    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(new Class[this.interfaces.size()]);
    }

    public boolean isInterfaceProxied(Class<?> targetInterface) {
        //判断是否是被代理的接口
        for (Class<?> proxyInterface : this.interfaces){
            if (targetInterface.isAssignableFrom(proxyInterface)){
                return true;
            }
        }
        return false;
    }

    public void addInterfaces(Class<?>[] classes){
        for (Class<?> clazz: classes){
            this.addInterface(clazz);
        }
    }

    public void addInterface(Class<?> clazz){
        //JDK代理，添加接口
        if (!clazz.isInterface()){
            throw new IllegalArgumentException("[" + clazz.getName() + "] is not an interface");
        }
        if (!interfaces.contains(clazz)){
            interfaces.add(clazz);
        }
    }

    public void setTargetObject(Object object) {
        this.targetObject = object;
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public Class<?> getTargetClass() {
        return this.targetObject.getClass();
    }
}
