package org.easyspring.aop.config;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.util.BeanUtils;
import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
public class MethodLocatingFactory {
    private String targetBeanName;
    private String methodName;
    private Method method;

    public MethodLocatingFactory(String targetBeanName,String methodName,BeanFactory beanFactory){
        this.targetBeanName = targetBeanName;
        this.methodName = methodName;
        this.positionMethod(beanFactory);
    }

    private void positionMethod(BeanFactory beanFactory){
        Class<?> beanClass = beanFactory.getType(this.targetBeanName);
        if (beanClass == null){
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName +"'");
        }
        this.method = BeanUtils.resolveSignature(this.methodName,beanClass);
        if (this.method == null){
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.targetBeanName + "]");
        }
    }

    public Method getObject() throws Exception{
        return this.method;
    }
}
