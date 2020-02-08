package org.easyspring.core.type;

import org.easyspring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author tancunshi
 */
public interface AnnotationMetaData extends ClassMetaData{
    AnnotationAttributes getAnnotationAttributes(String annotationType);
    Set<String> getAnnotationTypes();
    boolean hasAnnotation(String annotationType);
}
