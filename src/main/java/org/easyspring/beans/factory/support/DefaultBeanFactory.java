package org.easyspring.beans.factory.support;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.config.ConfigurableBeanFactory;
import org.easyspring.util.ClassUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tancunshi
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry{

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private ClassLoader classLoader = null;

    public DefaultBeanFactory() { }

    public void registerBeanDefinition(String beanId,BeanDefinition bd){
        this.beanDefinitionMap.put(beanId,bd);
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanId);
        if (bd == null) {
            throw new BeanCreationException("Bean Definition not exist");
        }

        if (bd.isSingleton()){
            Object bean = this.getSingletonBean(beanId);
            if (bean == null){
                bean = createBean(bd);
                this.registerSingletonBean(beanId,bean);
            }
            return bean;
        }
        return this.createBean(bd);
    }

    private Object createBean(BeanDefinition bd){
        String beanClassName = bd.getBeanClassName();

        ClassLoader cl = this.getClassLoader();
        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            return clazz.newInstance();
        }catch (Exception e){
            throw new BeanCreationException("Bean Definition not exist");
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader == null ? ClassUtil.getDefaultClassLoader() : this.classLoader;
    }
}
