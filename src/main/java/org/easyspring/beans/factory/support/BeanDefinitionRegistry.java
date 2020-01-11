package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;

/**
 * @author tancunshi
 */
public interface BeanDefinitionRegistry {
    public BeanDefinition getBeanDefinition(String beanId);

    public void registerBeanDefinition(String beanId, BeanDefinition bd);
}
