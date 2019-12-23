package org.easyspring.test.v2;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypedStringValue;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;
import org.easyspring.entity.Dog;
import org.easyspring.entity.Zoo;
import org.junit.Test;
import java.util.List;
import static junit.framework.TestCase.*;

public class BeanDefinitionTest {
    @Test
    public void propertyValueTest(){
        BeanFactory context = new ClassPathXmlApplicationContext("petstore-v2.xml");
        Zoo zoo =(Zoo) context.getBean("zoo");
        assertNotNull(zoo);

        Dog dog = zoo.getDog();
        assertNotNull(dog);

        String zooName = zoo.getZooName();
        assertEquals(zooName,"Crazy");
    }
    @Test
    public void propertyValueGet(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v2.xml");
        reader.loadBeanDefinition(resource);
        BeanDefinition zoo = factory.getBeanDefinition("zoo");
        List<PropertyValue> properties = zoo.getPropertyValues();
        assertEquals(properties.size(),2);

        Object o1 = this.getPropertyValue("dog",properties);
        assertTrue(o1 instanceof  RuntimeBeanReference);

        Object o2 = this.getPropertyValue("zooName",properties);
        assertTrue(o2 instanceof  TypedStringValue);
    }

    private PropertyValue getPropertyValue(String name,List<PropertyValue> pvs){
        for(PropertyValue pv : pvs){
            if(pv.getName().equals(name)){
                return pv;
            }
        }
        return null;
    }
}
