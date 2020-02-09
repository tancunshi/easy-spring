package org.easyspring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypedStringValue;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.support.GenericBeanDefinition;
import org.easyspring.context.annotation.ClassPathBeanDefinitionScanner;
import org.easyspring.core.io.Resource;
import org.easyspring.util.StringUtils;
import org.easyspring.beans.ConstructorArgument.ValueHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author tancunshi
 */
public class XmlBeanDefinitionReader {
    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE_ATTRIBUTE = "scope";
    private static final String PROPERTY_ELEMENT = "property";
    private static final String REF_ATTRIBUTE = "ref";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String PROPERTY_NAME_ATTRIBUTE = "name";
    private static final String CONSTRUCTOR_ARGS_ELEMENT = "constructor-arg";
    private static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
    private static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinition(Resource resource) {
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element root = document.getRootElement();

            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()) {
                Element ele = iter.next();

                //每个元素节点都有namespace，<bean/>属于默认命名空间，<context:component-scan/>属于context命名空间
                String namespaceUri = ele.getNamespaceURI();
                if (this.isDefaultNamespace(namespaceUri)){
                    //普通的bean
                    this.parseDefaultElement(ele);
                }else if (this.isContextNamespace(namespaceUri)){
                    //context命名空间的元素节点
                    this.parseComponentElement(ele);
                }
            }
        } catch (Throwable e) {
            throw new BeanDefinitionStoreException("BeanDefinition store error",e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseComponentElement(Element ele){
        //简化代码，且认为context命名空间只有component-scan一个元素节点
        String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this.registry);
        scanner.doScan(basePackages);
    }

    private void parseDefaultElement(Element ele){
        String beanId = ele.attributeValue(ID_ATTRIBUTE);
        String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
        BeanDefinition bd = new GenericBeanDefinition(beanId, beanClassName);
        if (ele.attributeValue(SCOPE_ATTRIBUTE) != null) {
            bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
        }
        this.parseConstructorElement(ele, bd);
        this.parsePropertyElement(ele, bd);
        this.registry.registerBeanDefinition(beanId, bd);
    }

    private void parseConstructorElement(Element beanElem, BeanDefinition bd) {
        Iterator iter = beanElem.elementIterator(CONSTRUCTOR_ARGS_ELEMENT);

        while (iter.hasNext()) {
            Element consElem = (Element) iter.next();
            Object consValue = this.parsePropertyValue(consElem, null);
            ValueHolder vh = new ValueHolder(consValue);
            bd.getConstructorArgument().addArgumentValues(vh);
        }
    }

    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator iter = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (iter.hasNext()) {
            Element propElem = (Element) iter.next();
            String propertyName = propElem.attributeValue(PROPERTY_NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                return;
            }

            Object val = parsePropertyValue(propElem, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);

            bd.addProperty(pv);
        }
    }

    private Object parsePropertyValue(Element ele, String propertyName) {
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";
        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
        boolean hasValAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);

        if (hasRefAttribute) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        } else if (hasValAttribute) {
            TypedStringValue strVal = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
            return strVal;
        } else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }

    private boolean isDefaultNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    private boolean isContextNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }
}
