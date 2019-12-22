package org.easyspring.beans.factory.config;

public interface SingletonBeanRegistry {
    void registerSingletonBean(String beanId,Object singletonObject);
    Object getSingletonBean(String beanId);
}
