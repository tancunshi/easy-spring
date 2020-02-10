package org.easyspring.beans.factory.config;

/**
 * @author tancunshi
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    void setClassLoader(ClassLoader classLoader);

    ClassLoader getClassLoader();
}
