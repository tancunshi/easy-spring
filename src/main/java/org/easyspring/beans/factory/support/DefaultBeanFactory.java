package org.easyspring.beans.factory.support;

import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.SimpleTypeConverter;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanFactoryAware;
import org.easyspring.beans.factory.BeanRegisterException;
import org.easyspring.beans.factory.NoSuchBeanDefinitionException;
import org.easyspring.beans.factory.config.*;
import org.easyspring.util.Assert;
import org.easyspring.util.ClassUtils;
import org.easyspring.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tancunshi
 */
public class DefaultBeanFactory extends AbstractBeanFactory
        implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    //ClassName到beanIds的映射
    private final Map<String, List<String>> classBeanIdMap = new HashMap<String, List<String>>(64);
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
    private ClassLoader classLoader = null;
    private List<String> syntheticBeanIds = new ArrayList<>();

    public DefaultBeanFactory() {
    }

    public void addBeanPostProcessor(BeanPostProcessor postProcessor){
        this.beanPostProcessors.add(postProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public void registerBeanDefinition(String beanId, BeanDefinition bd) {
        if (this.beanDefinitionMap.get(beanId) != null){
            throw new BeanRegisterException("The beanId " + beanId + " are repeated");
        }
        this.beanDefinitionMap.put(beanId, bd);
        List<String> beanIds;
        if ((beanIds = this.classBeanIdMap.get(bd.getBeanClassName())) == null){
            beanIds = new ArrayList<String>();
            this.classBeanIdMap.put(bd.getBeanClassName(),beanIds);
        }
        beanIds.add(beanId);
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanId);
        if (bd == null) {
            throw new BeanCreationException("BeanDefinition not exist");
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

    public Class<?> getType(String beanId) {
        if (!StringUtils.hasText(beanId)){
            throw new IllegalArgumentException("Property 'beanId' is required");
        }

        BeanDefinition bd = this.getBeanDefinition(beanId);
        if (bd == null){
            throw new NoSuchBeanDefinitionException(beanId);
        }

        try {
            Class<?> clazz = Class.forName(bd.getBeanClassName());
            return clazz;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class " + bd.getBeanClassName() + "not found", e);
        }
    }

    @Override
    public List<Object> getBeansByType(Class<?> clazz) {
        Assert.notNull(clazz,"Class must be exist");
        List<Object> result = new ArrayList<>();
        for (Map.Entry<String,BeanDefinition> entry: beanDefinitionMap.entrySet()){
            BeanDefinition bd = entry.getValue();
            if (clazz.isAssignableFrom(bd.getClazz())){
                result.add(this.getBean(entry.getKey()));
            }
        }
        return result;
    }

    private Object getBean(Class<?> clazz){
        String className = clazz.getName();
        List<String> beanIds = this.classBeanIdMap.get(className);
        if (beanIds == null || beanIds.size() == 0){
            throw new BeanCreationException("Not Found class " + className + " BeanDefinition");
        }
        else if (beanIds.size() > 1){
            throw new BeanCreationException("The Container have many " + className + " entity");
        }
        else {
            return this.getBean(beanIds.get(0));
        }
    }

    protected Object createBean(BeanDefinition bd) {
        //bean的生命周期，创建和装配流程
        Object bean = this.instantiateBean(bd);
        //属性注入，包括setter注入和autowired
        this.populateBean(bd, bean);
        //进行aware注入
        this.invokeAwareMethods(bean);
        //初始化，初始化完成就可以被使用了，这里拿到的可能是初始化完成之后产生的代理对象
        bean = this.initializeBean(bd,bean);
        return bean;
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

    private void populateBean(BeanDefinition bd, Object bean) {
        for(BeanPostProcessor processor : this.getBeanPostProcessors()){
            if(processor instanceof InstantiationAwareBeanPostProcessor){
                ((InstantiationAwareBeanPostProcessor)processor).postProcessPropertyValues(bean, bd.getID());
            }
        }

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

    protected Object initializeBean(BeanDefinition bd, Object bean)  {
        //postProcessor beforeInitializationBean
        //Todo，对Bean做初始化
        if(!bd.isSynthetic()){
            //如果是合成bean，没有必要进行后续处理，比如advice，pointcut
            //在afterInitializationBean中生成代理对象并返回，偷梁换柱
            return applyBeanPostProcessorsAfterInitialization(bean,bd.getID());
        }
        return bean;
    }

    private Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanId) {
        //postProcessor afterInitializationBean
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor: getBeanPostProcessors()){
            result = beanPostProcessor.afterInitialization(result, beanId);
            if (result == null) {
                return null;
            }
        }
        return result;
    }

    private void invokeAwareMethods(final Object bean) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader == null ? ClassUtils.getDefaultClassLoader() : this.classLoader;
    }

    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMath = descriptor.getDependencyType();
        return this.getBean(typeToMath);
    }
}
