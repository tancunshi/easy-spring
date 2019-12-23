package org.easyspring.test.v2;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class BeanDefinitionTest {
    @Test
    public void propertyValueTest(){
        BeanFactory context = new ClassPathXmlApplicationContext("petstore-v2.xml");

    }
}
