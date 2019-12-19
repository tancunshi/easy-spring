package org.easyspring.test;

import org.easyspring.BeanFactory;
import org.easyspring.service.PetStoreService;
import org.easyspring.support.BeanDefinition;
import org.easyspring.support.DefaultBeanFactory;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {
    @Test
    public void testGetBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertEquals("org.easyspring.v1.PetStoreService",bd.getBeanClassName());
        PetStoreService service = (PetStoreService)factory.getBean("petStore");
        assertNotNull(service);
    }
}
