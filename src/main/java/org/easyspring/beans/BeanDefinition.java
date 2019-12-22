package org.easyspring.beans;

/**
 * BeanDefinition 用于描述 Bean
 */
public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    String getBeanClassName();
    boolean isPrototype();
    boolean isSingleton();
    void setScope(String scope);
    String getScope();
}
