package org.easyspring.core.type.classreading;

import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.type.asm.AbstractAnnotationVisitor;
import java.util.Map;

/**
 * @author tancunshi
 */
public class AnnotationAttributesReadingVisitor extends AbstractAnnotationVisitor {

    private final String annotationName;
    private final Map<String,AnnotationAttributes> annotationsMap;
    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String className, Map<String, AnnotationAttributes> annotationsMap) {
        this.annotationName = className;
        this.annotationsMap = annotationsMap;
    }

    @Override
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName,attributeValue);
    }

    @Override
    public void visitEnd() {
        this.annotationsMap.put(this.annotationName,this.attributes);
    }
}
