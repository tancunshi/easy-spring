package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tancunshi
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;
    private String scope = SCOPE_DEFAULT;
    private boolean isSingleton = true;
    private boolean isPrototype = false;
    private List<PropertyValue> properties = new ArrayList<PropertyValue>();
    private ConstructorArgument consArgument = new ConstructorArgument();

    public GenericBeanDefinition(){}

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    public boolean isPrototype() {
        return this.isPrototype;
    }

    public boolean isSingleton() {
        return this.isSingleton;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.isSingleton = SCOPE_DEFAULT.equals(scope) || SCOPE_SINGLETON.equals(scope);
        this.isPrototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public String getScope() {
        return this.scope;
    }

    public void addProperty(PropertyValue property) {
        this.properties.add(property);
    }

    public List<PropertyValue> getPropertyValues() {
        return this.properties;
    }

    public ConstructorArgument getConstructorArgument() {
        return consArgument;
    }

    public boolean hasConstructorArgumentValues() {
        return this.consArgument.isEmpty() == false;
    }

    public String getID() {
        return this.id;
    }
}
