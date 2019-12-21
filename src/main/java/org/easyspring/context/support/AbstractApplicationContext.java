package org.easyspring.context.support;

import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.context.ApplicationContext;
import org.easyspring.core.io.Resource;

/**
 *  使用模板方法，消除重复代码
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;

    public AbstractApplicationContext(String configFile){
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinition(resource);
    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    protected abstract Resource getResourceByPath(String path);
}
