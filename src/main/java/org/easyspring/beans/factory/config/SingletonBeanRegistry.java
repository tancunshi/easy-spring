package org.easyspring.beans.factory.config;

/**
 * @author tancunshi
 */
public interface SingletonBeanRegistry {
    void registerSingletonBean(String beanId,Object singletonObject);
    Object getSingletonBean(String beanId);
}
