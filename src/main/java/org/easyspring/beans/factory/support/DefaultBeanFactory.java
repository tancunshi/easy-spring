package org.easyspring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.util.ClassUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  接口的实现是一个赋能的过程；
 *  按照面向对象的设计要求，一个类应该遵循单一职责原则，而履行一个职责需要不同的能力；
 *  比如DefaultBeanFactory 需要加载xml配置，解析bean节点，反射创建bean实例等能力；
 *  将这些能力划分到不同的接口上，比如BeanFactory，BeanDefinitionRegistry，
 *  对于BeanFactory的使用者而言，无需知道BeanDefinition的存在，也无需知道DefaultBeanFactory的其它能力；
 */
public class DefaultBeanFactory implements BeanFactory , BeanDefinitionRegistry{

    public static final String  ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile){
        InputStream is = null;
        try {
            ClassLoader cl = ClassUtil.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);

            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()){
                Element ele = iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                this.beanDefinitionMap.put(id,bd);
            }
        }catch (Exception e){
            throw new BeanDefinitionStoreException("IOException parsing XML document wrong");
        }finally {
            if (is != null){
                try {
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
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
