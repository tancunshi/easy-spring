package org.easyspring.context.support;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.core.io.FileSystemResource;
import org.easyspring.core.io.Resource;

public class FileSystemXmlApplicationContext implements BeanFactory {

    private DefaultBeanFactory factory = null;
    public FileSystemXmlApplicationContext(String configFile){
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new FileSystemResource(configFile);
        reader.loadBeanDefinition(resource);
    }
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }
}
