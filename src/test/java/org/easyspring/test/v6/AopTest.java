package org.easyspring.test.v6;

import org.easyspring.context.ApplicationContext;
import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.easyspring.test.aop.Animal;
import org.easyspring.test.aop.Controller;
import org.easyspring.util.MessageTracker;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class AopTest {

    @Before
    public void clear(){
        MessageTracker.clear();
    }

    @Test
    public void aopTest(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v5.xml");
        Controller controller = (Controller) applicationContext.getBean("controller");
        controller.sayHello();
        assertEquals(MessageTracker.getTracks(), Arrays.asList("start tx","hello","commit tx"));

        clear();
        Animal bird = (Animal) applicationContext.getBean("bird");
        bird.eat();
        assertEquals(MessageTracker.getTracks(), Arrays.asList("start tx","bird eat","after tx","commit tx"));
    }
}
