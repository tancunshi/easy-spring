package org.easyspring.aop.config;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.BeanFactoryAware;
import org.easyspring.beans.factory.FactoryBean;
import org.easyspring.beans.factory.NoSuchBeanDefinitionException;
import org.easyspring.util.BeanUtils;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 实现了FactoryBean这个接口说明，这个Bean是为了创建其它Bean而存在的
 * @author tancunshi
 */
public class MethodLocatingFactory implements FactoryBean<Method>, BeanFactoryAware {

    private String beanId;
    private String methodName;
    private Method method;

    public MethodLocatingFactory(){}

    public Method getObject() throws Exception{
        return this.method;
    }

    public Class<?> getObjectType() {
        return Method.class;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        Class<?> beanClass = beanFactory.getType(this.beanId);
        if (beanClass == null){
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.beanId +"'");
        }
        this.method = BeanUtils.resolveSignature(this.methodName,beanClass);
        if (this.method == null){
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.beanId + "]");
        }
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
