package org.easyspring.core.type.classreading;

import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.type.AnnotationMetaData;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author tancunshi
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetaData {
    //注解就是接口，annotationSet里存的都是类名
    private final Set<String> annotationNames = new LinkedHashSet<String>(4);
    private final Map<String, AnnotationAttributes> annotationsMap = new LinkedHashMap<String, AnnotationAttributes>(4);

    public AnnotationMetadataReadingVisitor() {

    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        //如果类被注解修饰则需要提供AnnotationVisitor
        String className = Type.getType(desc).getClassName();
        this.annotationNames.add(className);
        return new AnnotationAttributesReadingVisitor(className, this.annotationsMap);
    }

    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.annotationsMap.get(annotationType);
    }

    public Set<String> getAnnotationTypes() {
        return this.annotationNames;
    }

    public boolean hasAnnotation(String annotationType) {
        return this.annotationNames.contains(annotationType);
    }
}
