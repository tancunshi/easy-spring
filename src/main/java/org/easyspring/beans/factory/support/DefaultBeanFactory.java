package org.easyspring.beans.factory.support;

import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.config.ConfigurableBeanFactory;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypedStringValue;
import org.easyspring.util.ClassUtil;

import java.lang.reflect.Field;
import java.util.List;
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
            Object o = clazz.newInstance();
            this.fillBeanProperty(o,bd.getPropertyValues());
            return o;
        }catch (Exception e){
            throw new BeanCreationException("Bean Definition not exist");
        }
    }

    private void fillBeanProperty(Object o,List<PropertyValue> properties) throws Exception{
        for (PropertyValue property: properties){
            String propName = property.getName();
            Object value = property.getValue();
            Class<?> clazz = o.getClass();
            Field field = clazz.getDeclaredField(propName);
            field.setAccessible(true);
            if (value instanceof TypedStringValue){
                TypedStringValue strVal = (TypedStringValue) value;
                field.set(o,strVal.getValue());
            }

            if (value instanceof RuntimeBeanReference){
                RuntimeBeanReference refVal = (RuntimeBeanReference) value;
                if (!property.isConverted()){
                    Object refBean = this.getBean(refVal.getBeanName());
                    property.setConvertedValue(refBean);
                }
                field.set(o,property.getConvertedValue());
            }
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader == null ? ClassUtil.getDefaultClassLoader() : this.classLoader;
    }
}
