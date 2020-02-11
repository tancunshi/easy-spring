package org.easyspring.beans.factory.config;

import org.easyspring.beans.BeansException;

/**
 * Bean实例化后置处理器接口
 * @author tancunshi
 */
public interface BeanPostProcessor {

    //初始化方法执行之前调用
    Object beforeInitialization(Object bean,String beanName) throws BeansException;

    //初始化方法执行之后调用
    Object afterInitialization(Object bean,String beanName) throws BeansException;
}
