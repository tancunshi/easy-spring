package org.easyspring.test.v3;

import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.easyspring.entity.Zoo;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class ApplicationContextTest {
    @Test
    public void testGetBeanProperty(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("petstore-v3.xml");
        Zoo zoo =(Zoo) context.getBean("zoo");
        assertNotNull(zoo);
        assertNotNull(zoo.getDog());
        assertNotNull(zoo.getZooName());
        assertNotNull(zoo.getDog().getDogName());
    }
}
 