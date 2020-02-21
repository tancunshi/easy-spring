package org.easyspring.test.v5;

import org.easyspring.aop.config.MethodLocatingFactory;
import org.easyspring.context.ApplicationContext;
import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import java.lang.reflect.Method;
import static junit.framework.TestCase.*;

public class MethodLocatingFactoryTest {

    @Test
    public void locationMethod(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v5.xml");
        MethodLocatingFactory factory = new MethodLocatingFactory();
        factory.setBeanId("controller");
        factory.setMethodName("sayHello");
        factory.setBeanFactory(applicationContext);

        try {
            Method method = factory.getObject();
            assertEquals(method.getName(),"sayHello");
        }
        catch (Exception e){
            fail();
        }
    }

}
