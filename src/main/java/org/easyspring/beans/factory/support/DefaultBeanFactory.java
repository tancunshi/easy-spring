package org.easyspring.beans.factory.support;

import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.config.ConfigurableBeanFactory;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypedStringValue;
import org.easyspring.util.ClassUtils;

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
        return createBean(bd);
    }

    /**
     *  原先的createBean只是简单的反射创建Bean，
     *  现在额外多了一个设置属性值得功能，
     *  所以重构了createBean，
     *  拆解成两个方法
     */
    private Object createBean(BeanDefinition bd){
        Object bean = this.instantiateBean(bd);
        this.populateBean(bd,bean);
        return bean;
    }

    private Object instantiateBean(BeanDefinition bd){
        ClassLoader cl = this.getClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            return clazz.newInstance();
        }catch (Exception e){
            throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
        }
    }

    private void populateBean(BeanDefinition bd,Object bean) {
        List<PropertyValue> pvs = bd.getPropertyValues();

        if (pvs == null || pvs.isEmpty()){
            return;
        }

        try{
            for (PropertyValue pv: pvs){
                String propName = pv.getName();
                Object value = pv.getValue();
                Class<?> clazz = bean.getClass();
                Field field = clazz.getDeclaredField(propName);
                field.setAccessible(true);
                if (value instanceof TypedStringValue){
                    TypedStringValue strVal = (TypedStringValue) value;
                    field.set(bean,strVal.getValue());
                }

                if (value instanceof RuntimeBeanReference){
                    RuntimeBeanReference refVal = (RuntimeBeanReference) value;
                    if (!pv.isConverted()){
                        Object refBean = this.getBean(refVal.getBeanName());
                        pv.setConvertedValue(refBean);
                    }
                    Object conVal = pv.getConvertedValue();
                    field.set(bean,conVal);
                }
            }
        }catch (Exception e){
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", e);
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader == null ? ClassUtils.getDefaultClassLoader() : this.classLoader;
    }
}
