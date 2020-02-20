package org.easyspring.context.annotation;

import org.easyspring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.easyspring.beans.factory.support.GenericBeanDefinition;
import org.easyspring.core.type.AnnotationMetaData;

/**
 * @author tancunshi
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetaData metaData;

    public ScannedGenericBeanDefinition(AnnotationMetaData metaData) {
        super(metaData.getClassName());
        this.metaData = metaData;
    }

    public AnnotationMetaData getMetadata() {
        return this.metaData;
    }
}
