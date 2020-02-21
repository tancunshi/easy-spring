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
    private Class<?> clazz;
    private String scope = SCOPE_DEFAULT;
    private boolean isSingleton = true;
    private boolean isPrototype = false;
    private boolean isSynthetic = false;
    private List<PropertyValue> properties = new ArrayList<PropertyValue>();
    private ConstructorArgument consArgument = new ConstructorArgument();

    public GenericBeanDefinition(){}

    public GenericBeanDefinition(Class<?> clazz){
        this.setClazz(clazz);
    }

    public GenericBeanDefinition(String beanClassName){
        this.setBeanClassName(beanClassName);
    }

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.setBeanClassName(beanClassName);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.clazz = Class.forName(beanClassName);
        }
        catch (Exception e){
            throw new  RuntimeException(e);
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.beanClassName = clazz.getName();
        this.clazz = clazz;
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

    public void setSynthetic(boolean isSynthetic) {
        //是否是合成的，比如说aspect，非典型bean结构
        this.isSynthetic = isSynthetic;
    }

    public boolean isSynthetic() {
        return isSynthetic;
    }
}
