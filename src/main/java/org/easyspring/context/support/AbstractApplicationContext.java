package org.easyspring.context.support;

import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.context.ApplicationContext;
import org.easyspring.core.io.Resource;
import org.easyspring.util.ClassUtils;

/**
 * @author tancunshi
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;
    private ClassLoader classLoader = null;

    public AbstractApplicationContext(String configFile){
        factory = new DefaultBeanFactory();
        factory.setClassLoader(this.getClassLoader());
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinition(resource);
    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    protected abstract Resource getResourceByPath(String path);

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader == null ? ClassUtils.getDefaultClassLoader() : this.classLoader;
    }
}
