package org.easyspring.test.v2;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.PropertyValue;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypedStringValue;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.context.ApplicationContext;
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
    public void iocApplicationContext(){
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v2.xml");
        Zoo zoo = (Zoo) context.getBean("zoo");
        assertNotNull(zoo);
        assertEquals(zoo.getZooName(),"crazy");
        Dog dog = zoo.getDog();
        assertNotNull(dog);
        assertEquals(dog.getDogName(),"puppy");
        assertEquals(dog,context.getBean("dog"));
        assertEquals(dog.getDogAge(),new Integer(6));
        assertEquals(dog.getWeight(),new Double(1024.0));
        assertEquals(dog.isFemale(),true);
    }

    @Test
    public void propertyValueGet(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v2.xml");
        reader.loadBeanDefinition(resource);
        BeanDefinition bd = factory.getBeanDefinition("zoo");
        List<PropertyValue> properties = bd.getPropertyValues();
        assertEquals(properties.size(),2);

        PropertyValue pv1 = this.getPropertyValue("dog",properties);
        assertTrue(pv1.getValue() instanceof  RuntimeBeanReference);

        PropertyValue pv2 = this.getPropertyValue("zooName",properties);
        assertTrue(pv2.getValue() instanceof  TypedStringValue);
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
