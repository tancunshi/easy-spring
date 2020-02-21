package org.easyspring.beans.factory;

import java.util.List;

/**
 * @author tancunshi
 */
public interface BeanFactory {
    Object getBean(String beanId);
    Class<?> getType(String beanId);
    List<Object> getBeansByType(Class<?> clazz);
}
