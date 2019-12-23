package org.easyspring.test.v1;

import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.easyspring.context.support.FileSystemXmlApplicationContext;
import org.easyspring.entity.PetStore;
import static junit.framework.TestCase.assertNotNull;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void testGetBean(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStore service = (PetStore) applicationContext.getBean("petStore");
        assertNotNull(service);
    }

    public void testGetBeanFromFileSystem(){
        FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("E:/petstore-v1.xml");
        PetStore service = (PetStore)applicationContext.getBean("petStore");
        assertNotNull(service);
    }
}
