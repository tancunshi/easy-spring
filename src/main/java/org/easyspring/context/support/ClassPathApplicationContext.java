package org.easyspring.context.support;

import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.context.ApplicationContext;

public class ClassPathApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;

    public ClassPathApplicationContext(String configFile){
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(configFile);
    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }
}
