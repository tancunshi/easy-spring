package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;

/**
 * 注入target的Field Metadata的抽象
 * @author tancunshi
 */
public class AutowiredFieldElement extends AbstractAutowiredInjectionElement {

    public AutowiredFieldElement(Member field, AutowireCapableBeanFactory factory, Annotation annotation) {
        super(field,factory,annotation);
    }

    @Override
    public void inject(Object target) {
        Autowired annotation = this.getAnnotation();
        Field field = this.getField();
        try {
            DependencyDescriptor dependency = new DependencyDescriptor(field,annotation.required());
            Object value = super.resolveDependency(dependency);
            if (value != null){
                ReflectionUtils.makeAccessible(field);
                field.set(target,value);
            }
        }
        catch (Throwable ex){
            ex.printStackTrace();
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }

    private Autowired getAnnotation(){
        return (Autowired) annotation;
    }

    private Field getField() {
        return (Field)this.member;
    }

}
