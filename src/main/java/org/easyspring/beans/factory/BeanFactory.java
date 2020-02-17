package org.easyspring.beans.factory;

/**
 * @author tancunshi
 */
public interface BeanFactory {
    Object getBean(String beanId);
    Class<?> getType(String beanId);
}
