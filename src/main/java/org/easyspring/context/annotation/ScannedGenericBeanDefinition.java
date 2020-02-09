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
        //beanClassName是BeanDefinition中重要的属性，必不可少。构造器中参数都是不可或缺的参数，否则一般通过set注入
        super.setBeanClassName(metaData.getClassName());
        this.metaData = metaData;
    }

    public AnnotationMetaData getMetadata() {
        return this.metaData;
    }
}
