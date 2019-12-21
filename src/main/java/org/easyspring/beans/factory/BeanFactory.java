package org.easyspring.beans.factory;

import org.easyspring.beans.BeanDefinition;

public interface BeanFactory {
    Object getBean(String beanId);
}
