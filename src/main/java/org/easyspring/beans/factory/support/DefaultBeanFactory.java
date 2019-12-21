package org.easyspring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.util.ClassUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  重构是一个职能剥离的过程
 *  按照面向对象的设计要求，一个类应该遵循单一职责原则；
 *  拆分DefaultBeanFactory解析配置的职责，解析配置由XmlBeanDefinitionReader完成；
 *  DefaultBeanFactory只提供创建Bean实例的职责；
 *
 *  而创建Bean实例需要不同的能力，比如registerBeanDefinition，反射创建Bean;
 *  将这些能力划分到不同的接口上，通过实现接口的方式赋能，比如BeanFactory，BeanDefinitionRegistry，
 *  对于BeanFactory的使用者而言，无需知道BeanDefinition的存在，也无需知道DefaultBeanFactory的其它能力；
 */
public class DefaultBeanFactory implements BeanFactory , BeanDefinitionRegistry{

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

    public DefaultBeanFactory() { }

    public void registerBeanDefinition(String beanId,BeanDefinition bd){
        this.beanDefinitionMap.put(beanId,bd);
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanId);
        if (bd == null) throw new BeanCreationException("Bean Definition not exist");
        ClassLoader cl = ClassUtil.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();

        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            return clazz.newInstance();
        }catch (Exception e){
            throw new BeanCreationException("Bean Definition not exist");
        }
    }
}
