package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.util.ReflectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
public class AutowiredMethodElement extends AbstractAutowiredInjectionElement{

    public AutowiredMethodElement(Member method, AutowireCapableBeanFactory factory, Annotation annotation){
        super(method,factory,annotation);
    }

    public void inject(Object target) {
        Autowired autowired = this.getAnnotation();
        Method method = this.getMethod();
        try {
            DependencyDescriptor dependency = new DependencyDescriptor(method,autowired.required());
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

    private Method getMethod(){
        return (Method) member;
    }

    private Autowired getAnnotation(){
        return (Autowired) annotation;
    }
}
