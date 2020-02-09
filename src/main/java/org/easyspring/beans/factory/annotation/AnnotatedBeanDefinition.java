package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.core.type.AnnotationMetaData;

/**
 * @author tancunshi
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetaData getMetadata();
}
