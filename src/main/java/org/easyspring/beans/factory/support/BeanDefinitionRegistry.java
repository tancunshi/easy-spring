package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;

/**
 *  从BeanFactory中剥离关于BeanDefinition的方法，对BeanFactory隐藏BeanDefinition的存在
 */
public interface BeanDefinitionRegistry {
    public BeanDefinition getBeanDefinition(String beanId);
}
