package org.easyspring.test.v1;

import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.easyspring.service.PetStoreService;
import static junit.framework.TestCase.assertNotNull;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void testGetBean(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService service = (PetStoreService) applicationContext.getBean("petStore");
        assertNotNull(service);
    }
}
