package org.easyspring.test.v4;

import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;
import org.easyspring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.easyspring.core.type.classreading.ClassMetadataReadingVisitor;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.awt.*;

import static junit.framework.TestCase.*;

public class ClassReaderTest {

    @Test
    public void testGetClassMethodData() throws Exception{
        Resource resource = new ClassPathResource("org/easyspring/test/entity/Dog.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        reader.accept(visitor,true);

        assertFalse(visitor.isAbstract());
        assertFalse(visitor.isInterface());
        assertFalse(visitor.isFinal());
        assertEquals("org.easyspring.test.entity.Dog",visitor.getClassName());
        assertEquals("java.lang.Object",visitor.getSuperClassName());
        assertEquals(0,visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnotation() throws Exception{
        Resource resource = new ClassPathResource("org/easyspring/test/entity/Person.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor,true);

        assertTrue(visitor.getAnnotationTypes().size() == 1);
        assertTrue(visitor.hasAnnotation("org.easyspring.stereotype.Component"));
        AnnotationAttributes attributes = visitor.getAnnotationAttributes("org.easyspring.stereotype.Component");
        assertEquals(attributes.getString("value"),"user");
    }
}
