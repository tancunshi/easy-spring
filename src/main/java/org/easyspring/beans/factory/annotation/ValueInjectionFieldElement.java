package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.util.ReflectionUtils;
import org.easyspring.util.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;

/**
 * @author tancunshi
 */
public class ValueInjectionFieldElement extends AbstractAutowiredInjectionElement{

    public ValueInjectionFieldElement(Member field, AutowireCapableBeanFactory factory, Annotation annotation){
        super(field,factory,annotation);
    }

    public void inject(Object target) {
        Value valueAnnotation = this.getAnnotation();
        Field field = this.getField();
        Object object = StringUtils.transferStringToObject(valueAnnotation.value(),field.getType());
        try {
            ReflectionUtils.makeAccessible(field);
            field.set(target,object);
        }
        catch (Throwable ex){
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }

    private Field getField() {
        return (Field) super.member;
    }

    private Value getAnnotation(){
        return (Value) annotation;
    }
}
