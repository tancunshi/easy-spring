package org.easyspring.beans.factory.support;

import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.SimpleTypeConverter;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanRegisterException;
import org.easyspring.beans.factory.annotation.Autowired;
import org.easyspring.beans.factory.config.ConfigurableBeanFactory;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.context.annotation.ScannedGenericBeanDefinition;
import org.easyspring.util.ClassUtils;
import org.easyspring.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tancunshi
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private ClassLoader classLoader = null;

    public DefaultBeanFactory() {
    }

    public void registerBeanDefinition(String beanId, BeanDefinition bd) {
        if (this.beanDefinitionMap.get(beanId) != null){
            throw new BeanRegisterException("The bean id "+beanId+" are repeated");
        }
        this.beanDefinitionMap.put(beanId, bd);
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanId);
        if (bd == null) {
            throw new BeanCreationException("Bean Definition not exist");
        }

        if (bd.isSingleton()) {
            Object bean = this.getSingletonBean(beanId);
            if (bean == null) {
                bean = createBean(bd);
                this.registerSingletonBean(beanId, bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    /**
     * 原先的createBean只是简单的反射创建Bean，
     * 现在额外多了一个设置属性值得功能，
     * 所以重构了createBean，
     * 拆解成两个方法
     */
    private Object createBean(BeanDefinition bd) {
        Object bean = this.instantiateBean(bd);
        if (bd instanceof ScannedGenericBeanDefinition){
            this.autowireProperties(bd,bean);
        }
        this.populateBean(bd, bean);
        return bean;
    }

    private void autowireProperties(BeanDefinition bd,Object bean)  {
        Class beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields){
            Autowired annotation = null;
            if (( annotation = field.getAnnotation(Autowired.class) ) != null){

                String refBeanName = annotation.value();
                if (!StringUtils.hasLength(refBeanName)){
                    refBeanName = field.getName();
                }
                RuntimeBeanReference ref = new RuntimeBeanReference(refBeanName);
                PropertyValue propValue = new PropertyValue(field.getName(),ref);
                bd.getPropertyValues().add(propValue);

            }
        }
    }

    private Object instantiateBean(BeanDefinition bd) {

        if (bd.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        } else {
            ClassLoader cl = this.getClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                Class<?> clazz = cl.loadClass(beanClassName);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
            }
        }
    }

    /**
     * 这里还可以直接使用common-beanutils下的
     * BeanUtils.setProperty进行注入
     */
    private void populateBean(BeanDefinition bd, Object bean) {
        List<PropertyValue> pvs = bd.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue pv : pvs) {
                String propName = pv.getName();
                Object originalVal = pv.getValue();
                Object resolveVal = valueResolver.resolveValueIfNecessary(originalVal);

                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propName)) {
                        Object convertedValue = converter.convertIfNecessary(resolveVal, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
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
