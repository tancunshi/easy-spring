package org.easyspring.context.annotation;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.easyspring.beans.factory.support.BeanDefinitionRegistry;
import org.easyspring.beans.factory.support.BeanNameGenerator;
import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.stereotype.Component;
import org.easyspring.util.ClassUtils;
import org.easyspring.util.StringUtils;
import java.beans.Introspector;
import java.util.Map;
import java.util.Set;

/**
 * @author tancunshi
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {
    public String generateBeanName(BeanDefinition definition) {
        if (definition instanceof AnnotatedBeanDefinition) {
            //AnnotatedBeanDefinition包含注解元数据
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            //判断注解中是否设置了beanName
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        return buildDefaultBeanName(definition);
    }

    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
        AnnotationMetaData amd = annotatedDef.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type : types) {
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if(isStereotypeWithNameValue(type,attributes)){
                String value = attributes.getString("value");
                if (StringUtils.hasLength(value)) {
                    beanName = value;
                }
            }
        }
        return beanName;
    }

    protected boolean isStereotypeWithNameValue(String annotationType, Map<String, Object> attributes) {

        //判断annotationType注解是否包含可以给bean命名的属性
        boolean isStereotype = annotationType.equals(Component.class.getName()); /*||
				(metaAnnotationTypes != null && metaAnnotationTypes.contains(COMPONENT_ANNOTATION_CLASSNAME)) ||
				annotationType.equals("javax.annotation.ManagedBean") ||
				annotationType.equals("javax.inject.Named");*/

        return (isStereotype && attributes != null && attributes.containsKey("value"));
    }


    protected String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
