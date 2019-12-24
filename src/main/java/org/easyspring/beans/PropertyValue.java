package org.easyspring.beans;

/**
 * @author tancunshi
 */
public class PropertyValue {
    private String name;
    private Object value;

    public PropertyValue(String name,Object value){
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
