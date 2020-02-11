package org.easyspring.context.support;

import org.easyspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.easyspring.beans.factory.config.BeanPostProcessor;
import org.easyspring.beans.factory.config.ConfigurableBeanFactory;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.context.ApplicationContext;
import org.easyspring.core.io.Resource;
import org.easyspring.util.ClassUtils;

import java.util.List;

/**
 * @author tancunshi
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;
    private ClassLoader classLoader = null;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        factory.setClassLoader(this.getClassLoader());
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinition(resource);
        registerBeanPostProcessors(factory);
    }

    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.factory.addBeanPostProcessor(postProcessor);
    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    protected abstract Resource getResourceByPath(String path);

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    protected ClassLoader getClassLoader() {
        return this.classLoader == null ? ClassUtils.getDefaultClassLoader() : this.classLoader;
    }

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {

        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);
    }
}
