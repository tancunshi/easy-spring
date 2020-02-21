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

    void addProperty(PropertyValue property);

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArgumentValues();

    String getID();

    void setId(String id);

    void setBeanClassName(String beanClassName);

    boolean isSynthetic();

    Class<?> getClazz();

    void setClazz(Class<?> clazz);

    void setSynthetic(boolean isSynthetic);
}
