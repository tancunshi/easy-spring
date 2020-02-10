package org.easyspring.test.v4;

import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.support.DefaultBeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;
import org.easyspring.test.entity.Person;
import org.easyspring.test.entity.School;
import org.junit.Test;
import static junit.framework.TestCase.*;
import java.lang.reflect.Field;

public class DependencyDescriptorTest {

    @Test
    public void testResolveDependency() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Field field = Person.class.getDeclaredField("school");
        DependencyDescriptor descriptor = new DependencyDescriptor(field,true);
        Object o = factory.resolveDependency(descriptor);
        assertTrue(o instanceof School);
    }
}
