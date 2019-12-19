package org.easyspring;

import org.easyspring.support.BeanDefinition;

public interface BeanFactory {
    BeanDefinition getBeanDefinition(String beanId);

    Object getBean(String beanId);
}
