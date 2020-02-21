package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.util.StringUtils;

public class BeanDefinitionReaderUtils {

    public static final String GENERATED_BEAN_NAME_SEPARATOR = "#";

    public static String registerWithGeneratedName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String generatedName = generatedBeanName(definition, registry, false);
        registry.registerBeanDefinition(generatedName, definition);
        return generatedName;
    }

    public static String generatedBeanName(BeanDefinition definition, BeanDefinitionRegistry registry, boolean isInnerBean) {
        String generatedBeanName = definition.getBeanClassName();
        if (!StringUtils.hasLength(generatedBeanName)) {
            throw new BeanDefinitionStoreException("Unnamed bean definition specifies neither " +
                    "'class' nor 'parent' nor 'factory-bean' - can't generate bean name");
        }

        if (isInnerBean){
            return generatedBeanName + GENERATED_BEAN_NAME_SEPARATOR + Integer.toHexString(System.identityHashCode(definition));
        }
        else {
            String id = generatedBeanName;
            int counter = -1;
            while (counter == -1 || ( registry.getBeanDefinition(id)!=null ) ) {
                counter++;
                id = generatedBeanName + GENERATED_BEAN_NAME_SEPARATOR + counter;
            }

            return id;
        }
    }
}
