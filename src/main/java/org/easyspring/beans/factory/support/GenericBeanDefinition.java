package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;
    private String scope = SCOPE_DEFAULT;
    private boolean isSingleton = true;
    private boolean isPrototype = false;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
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
}
