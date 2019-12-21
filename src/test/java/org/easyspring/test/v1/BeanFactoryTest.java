package org.easyspring.test.v1;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.service.PetStoreService;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class BeanFactoryTest {

    @Test
    public void testGetBean(){
        BeanDefinitionRegistry registry = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = registry.getBeanDefinition("petStore");
        assertEquals("org.easyspring.service.PetStoreService",bd.getBeanClassName());

        BeanFactory factory = (BeanFactory) registry;
        PetStoreService service = (PetStoreService) factory.getBean("petStore");
        assertNotNull(service);
    }

}
