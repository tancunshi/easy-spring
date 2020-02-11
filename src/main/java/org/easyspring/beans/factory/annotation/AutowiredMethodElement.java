package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.util.ReflectionUtils;
import java.lang.reflect.Method;

public class AutowiredMethodElement extends AbstractAutowiredInjectionElement{

    boolean required;

    public AutowiredMethodElement(Method method, boolean required, AutowireCapableBeanFactory factory){
        super(method,factory);
        this.required = required;
    }

    public Method getMethod(){
        return (Method) member;
    }

    public void inject(Object target) {
        Method method = this.getMethod();
        try {
            DependencyDescriptor dependency = new DependencyDescriptor(method,this.required);
            Object value = super.resolveDependency(dependency);
            if (value != null){
                ReflectionUtils.makeAccessible(method);
                method.invoke(target,value);
            }
        }
        catch (Throwable ex){
            throw new BeanCreationException("Could not autowire field: " + method, ex);
        }
    }
}
