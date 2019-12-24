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
import org.easyspring.core.io.Resource;
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
    private static final String REF_ATTRIBUTE = "ref";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String PROPERTY_NAME_ATTRIBUTE = "name";

    private BeanDefinitionRegistry registry;
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void loadBeanDefinition(Resource resource){
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element root = document.getRootElement();

            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()){
                Element ele = iter.next();
                String beanId = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(beanId,beanClassName);
                if (ele.attributeValue(SCOPE_ATTRIBUTE) != null){
                    bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.parsePropertyElement(ele,bd);
                this.registry.registerBeanDefinition(beanId,bd);
            }
        }catch (Exception e){
            throw new BeanDefinitionStoreException("IOException parsing XML document wrong");
        }finally {
            if (is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void parsePropertyElement(Element beanElem,BeanDefinition bd){
        Iterator iter = beanElem.elementIterator();
        while (iter.hasNext()){
             Element propElem = (Element) iter.next();
             String name = propElem.attributeValue(PROPERTY_NAME_ATTRIBUTE);
             PropertyValue property = null;
             if (propElem.attributeValue(VALUE_ATTRIBUTE) != null){
                 TypedStringValue value = new TypedStringValue(propElem.attributeValue(VALUE_ATTRIBUTE));
                 property = new PropertyValue(name,value);
             }else if (propElem.attributeValue(REF_ATTRIBUTE) != null){
                 RuntimeBeanReference ref = new RuntimeBeanReference(propElem.attributeValue(REF_ATTRIBUTE));
                 property = new PropertyValue(name,ref);
             }
             bd.addProperty(property);
        }
    }
}
