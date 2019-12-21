package org.easyspring.test.v1;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.service.PetStoreService;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void readerXml(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean(){
        reader.loadBeanDefinition("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        assertEquals("org.easyspring.service.PetStoreService",bd.getBeanClassName());

        PetStoreService service = (PetStoreService) factory.getBean("petStore");
        assertNotNull(service);
    }

    @Test
    public void testInvalidBean(){
        reader.loadBeanDefinition("petstore-v1.xml");
        try {
            factory.getBean("notExist");
        }catch (BeanCreationException e){
            return;
        }
        fail("expect BeanCreateException");
    }

    @Test
    public void testInvalidXML(){
        try {
            reader.loadBeanDefinition("xxx.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        fail("expect BeanDefinitionException");
    }
}
