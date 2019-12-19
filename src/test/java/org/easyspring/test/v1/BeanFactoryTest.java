package org.easyspring.test.v1;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.service.PetStoreService;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class BeanFactoryTest {

    @Test
    public void testGetBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertEquals("org.easyspring.service.PetStoreService",bd.getBeanClassName());
        PetStoreService service = (PetStoreService)factory.getBean("petStore");
        assertNotNull(service);
    }

}
