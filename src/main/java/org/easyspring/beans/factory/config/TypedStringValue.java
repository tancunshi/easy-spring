package org.easyspring.beans.factory.config;

/**
 * @author tancunshi
 */
public class TypedStringValue {
    private String value;
    public TypedStringValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
