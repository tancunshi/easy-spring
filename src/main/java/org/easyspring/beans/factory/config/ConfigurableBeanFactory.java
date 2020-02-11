package org.easyspring.beans.factory.config;

import java.util.List;

/**
 * @author tancunshi
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    void setClassLoader(ClassLoader classLoader);

    ClassLoader getClassLoader();

    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    List<BeanPostProcessor> getBeanPostProcessors();
}
