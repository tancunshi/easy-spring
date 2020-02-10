package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;

/**
 * @author tancunshi
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanId);

    void registerBeanDefinition(String beanId, BeanDefinition bd);
}
