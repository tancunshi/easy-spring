package org.easyspring.test.v4;

import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.easyspring.test.entity.Person;
import org.easyspring.test.entity.School;
import org.easyspring.test.entity.Zoo;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class ApplicationContextTest {

    /**
     * 这个测试用例用于测试自动包扫描
     */
    @Test
    public void componentScanTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("petstore-v4.xml");
        Person person = (Person) context.getBean("user");
        assertNotNull(person);
        School school = (School) context.getBean("School");
        assertNotNull(school);
    }
}
 