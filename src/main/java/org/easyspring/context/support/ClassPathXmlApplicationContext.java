package org.easyspring.context.support;

import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.context.ApplicationContext;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;

    public ClassPathXmlApplicationContext(String configFile){
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinition(resource);
    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }
}
