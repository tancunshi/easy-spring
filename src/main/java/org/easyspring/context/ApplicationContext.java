package org.easyspring.context;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.config.BeanPostProcessor;

/**
 * @author tancunshi
 */
public interface ApplicationContext extends BeanFactory {
    void addBeanPostProcessor(BeanPostProcessor postProcessor);
    void setClassLoader(ClassLoader classLoader);
}
