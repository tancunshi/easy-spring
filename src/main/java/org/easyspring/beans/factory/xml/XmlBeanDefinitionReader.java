package org.easyspring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.support.GenericBeanDefinition;
import org.easyspring.core.io.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {
    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE_ATTRIBUTE = "scope";
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
                Element element = iter.next();
                String beanId = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(beanId,beanClassName);
                if (element.attributeValue(SCOPE_ATTRIBUTE) != null){
                    bd.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
                }
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
}
