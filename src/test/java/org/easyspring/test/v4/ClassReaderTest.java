package org.easyspring.test.v4;

import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.core.type.ClassMetaData;
import org.easyspring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.easyspring.core.type.classreading.ClassMetadataReadingVisitor;
import org.easyspring.core.type.classreading.SimpleMetaDataReader;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;
import java.util.Set;

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
        String annotationType = "org.easyspring.stereotype.Component";

        Resource resource = new ClassPathResource("org/easyspring/test/entity/Person.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor,true);

        assertTrue(visitor.getAnnotationTypes().size() == 1);
        assertTrue(visitor.hasAnnotation(annotationType));
        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotationType);
        assertEquals(attributes.getString("value"),"user");
    }

    @Test
    public void testSimpleReader() throws IOException {
        String annotationType = "org.easyspring.stereotype.Component";
        String className = "org.easyspring.test.entity.Person";

        Resource resource = new ClassPathResource("org/easyspring/test/entity/Person.class");
        SimpleMetaDataReader reader = new SimpleMetaDataReader(resource);
        AnnotationMetaData metaData = reader.getAnnotationMetaData();

        Set<String> annotationTypes = metaData.getAnnotationTypes();
        assertEquals(annotationTypes.size(),1);
        assertTrue(metaData.hasAnnotation(annotationType));

        AnnotationAttributes attributes = metaData.getAnnotationAttributes(annotationType);
        assertEquals(attributes.getString("value"),"user");

        ClassMetaData classMetaData = reader.getClassMetadata();
        assertEquals(classMetaData.getClassName(),className);
        assertEquals(classMetaData.getSuperClassName(),"java.lang.Object");
        assertEquals(classMetaData.isFinal(),false);
    }
}
