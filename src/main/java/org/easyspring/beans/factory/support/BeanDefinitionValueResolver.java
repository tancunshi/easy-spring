package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.FactoryBean;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {
    private final AbstractBeanFactory factory;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = this.factory.getBean(refName);
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else if (value instanceof BeanDefinition){
            //value类型为BeanDefinition的情况适用于，局部使用的bean，所谓局部使用就是仅仅被一个bean使用
            //既然只被一个bean使用，那么为什么不直接new？
            //是为了让这个bean走ioc容器createBean的流程，只有走createBean的流程才能进行装配
            BeanDefinition bd = (BeanDefinition) value;
            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));
            return resolveInnerBean(innerBeanName, bd);
        } else {
            return value;
        }
    }

    private Object resolveInnerBean(String innerBeanName, BeanDefinition bd) {
        try {
            Object innerBean = this.factory.createBean(bd);
            //实现了FactoryBean接口需要再一次创建bean，才能得到真正需要的bean
            if (innerBean instanceof FactoryBean){
                try {
                    return ((FactoryBean) innerBean).getObject();
                }
                catch (Exception e){
                    throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
                }
            }
            else {
                return innerBean;
            }
        }
        catch (Exception e){
            throw new BeanCreationException(
                    innerBeanName,
                    "Cannot create inner bean '" + innerBeanName + "' " +
                            (bd != null && bd.getBeanClassName() != null ? "of type [" + bd.getBeanClassName() + "] " : ""), e);
        }
    }
}
