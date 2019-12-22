package org.easyspring.beans.factory.config;

import org.easyspring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {
    public void setClassLoader(ClassLoader classLoader);
    public ClassLoader getClassLoader();
}
