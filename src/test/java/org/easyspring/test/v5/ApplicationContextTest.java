package org.easyspring.test.v5;

import org.easyspring.context.ApplicationContext;
import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.easyspring.test.aop.Controller;
import org.easyspring.util.MessageTracker;
import org.junit.Test;
import java.util.Arrays;
import static junit.framework.TestCase.*;

public class ApplicationContextTest {

    @Test
    public void testSayHello(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v5.xml");
        Controller controller = (Controller) applicationContext.getBean("controller");
        assertNotNull(controller);

        controller.sayHello();

        assertEquals(Arrays.asList("start tx","hello","commit tx"), MessageTracker.getTracks());
    }
}
