package org.easyspring.test.v1;

import org.easyspring.context.support.ClassPathApplicationContext;
import org.easyspring.service.PetStoreService;
import static junit.framework.TestCase.assertNotNull;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void testGetBean(){
        ClassPathApplicationContext applicationContext = new ClassPathApplicationContext("petstore-v1.xml");
        PetStoreService service = (PetStoreService) applicationContext.getBean("petStore");
        assertNotNull(service);
    }
}
