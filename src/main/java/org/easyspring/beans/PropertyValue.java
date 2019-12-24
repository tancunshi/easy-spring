package org.easyspring.beans;

/**
 * @author tancunshi
 */
public class PropertyValue {
    private String name;
    private Object value;
    private Object convertedValue;

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

    public boolean isConverted(){
        return this.convertedValue != null;
    }

    public void setConvertedValue(Object value){
        this.convertedValue = value;
    }

    public Object getConvertedValue(){
        return this.convertedValue;
    }
}
