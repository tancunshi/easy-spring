package org.easyspring.beans.factory.config;

import org.easyspring.beans.BeansException;

/**
 * 感知bean实例化的处理器
 * @author tancunshi
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    //实例化之前调用
    Object beforeInstantiation(Class<?> beanClass,String beanName) throws BeansException;

    //实例化之后调用
    boolean afterInstantiation(Object bean,String beanName) throws BeansException;

    //感知到实例创建完成，进行属性值注入
    void postProcessPropertyValues(Object bean,String beanName) throws BeansException;
}
