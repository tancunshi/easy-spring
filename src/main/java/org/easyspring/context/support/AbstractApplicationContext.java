package org.easyspring.context.support;

import org.easyspring.aop.aspectj.AspectJAutoProxyCreator;
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
    private final DefaultBeanFactory factory;
    private ClassLoader classLoader = null;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
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
        this.factory.setClassLoader(classLoader);
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader(){
        return classLoader == null ? ClassUtils.getDefaultClassLoader() : this.classLoader;
    }

    public Class<?> getType(String beanId) {
        return this.factory.getType(beanId);
    }

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {

        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);

        AspectJAutoProxyCreator proxyCreator = new AspectJAutoProxyCreator();
        proxyCreator.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(proxyCreator);
    }

    @Override
    public List<Object> getBeansByType(Class<?> clazz) {
        return null;
    }
}
