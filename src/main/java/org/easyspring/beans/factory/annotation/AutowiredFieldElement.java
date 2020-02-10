package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.util.ReflectionUtils;
import java.lang.reflect.Field;

/**
 * 注入target的Metadata的抽象
 * @author tancunshi
 */
public class AutowiredFieldElement extends AbstractInjectionElement {

    boolean required;

    public AutowiredFieldElement(Field field, boolean b, DefaultBeanFactory factory) {
        super(field,factory);
    }

    public Field getField(){
        return (Field)this.member;
    }

    @Override
    public void inject(Object target) {
        //target是被注入的目标对象
        Field field = this.getField();
        try {
            DependencyDescriptor desc = new DependencyDescriptor(field,this.required);
            Object value = factory.resolveDependency(desc);
            if (value != null){
                ReflectionUtils.makeAccessible(field);
                field.set(target,value);
            }
        }
        catch (Throwable ex){
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }
}
