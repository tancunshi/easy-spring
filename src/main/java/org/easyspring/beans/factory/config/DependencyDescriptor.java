package org.easyspring.beans.factory.config;

import org.easyspring.util.Assert;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
public class DependencyDescriptor {
    private Field field;
    private Method method;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field,"Field must not be null");
        this.field = field;
        this.required = required;
    }

    public DependencyDescriptor(Method method, boolean required) {
        Assert.notNull(method,"Field must not be null");
        this.method = method;
        this.required = required;
    }

    public Class<?> getDependencyType(){
        if (this.field != null){
            return field.getType();
        }

        if (this.method != null){
            Class[] clazz = method.getParameterTypes();
            if (clazz.length != 1){
                throw new RuntimeException("cannot resolve this method dependency");
            }
            return clazz[0];
        }

        throw new RuntimeException("cannot resolve dependency");
    }

    public boolean isRequired(){
        return this.required;
    }
}
