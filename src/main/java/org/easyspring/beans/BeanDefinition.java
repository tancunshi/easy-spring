package org.easyspring.beans;

import java.util.List;

/**
 * @author tancunshi
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
    List<PropertyValue> getPropertyValues();
}
