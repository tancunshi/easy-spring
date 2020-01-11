package org.easyspring.test.v1;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.core.io.ClassPathResource;
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
    public void readerXml() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        assertEquals("org.easyspring.entity.PetStore", bd.getBeanClassName());

        Object o1 = factory.getBean("petStore");
        Object o2 = factory.getBean("petStore");
        assertNotNull(o1);
        assertEquals(o1, o2);
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        try {
            factory.getBean("notExist");
        } catch (BeanCreationException e) {
            return;
        }
        fail("expect BeanCreateException");
    }

    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinition(new ClassPathResource("xxx.xml"));
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        fail("expect BeanDefinitionException");
    }
}
