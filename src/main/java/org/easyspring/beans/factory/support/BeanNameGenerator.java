package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;

/**
 * @author tancunshi
 */
public interface BeanNameGenerator {

    String generateBeanName(BeanDefinition definition);

}
