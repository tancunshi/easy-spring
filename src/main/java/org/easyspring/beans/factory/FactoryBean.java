package org.easyspring.beans.factory;

public interface FactoryBean <T>{

    T getObject() throws Exception;
    Class<?> getObjectType();
}
