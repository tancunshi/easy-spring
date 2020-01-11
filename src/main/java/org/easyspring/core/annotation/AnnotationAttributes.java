package org.easyspring.core.annotation;

import org.easyspring.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class AnnotationAttributes extends LinkedHashMap {
    public AnnotationAttributes(){}

    public AnnotationAttributes(int initialCapacity){
        super(initialCapacity);
    }

    public AnnotationAttributes(Map<String,Object> map){
        super(map);
    }

    public String getString(String attributeName){
        return null;
    }

    public String[] getStringArray(String attributeName){
        return this.doGet(attributeName,String[].class);
    }

    public boolean getBoolean(String attributeName){
        return this.doGet(attributeName,Boolean.class);
    }

    @SuppressWarnings("unchecked")
    public <N extends Number> N getNumber(String attributeName){
        return (N) this.doGet(attributeName,Number.class);
    }

    @SuppressWarnings("unchecked")
    public <E extends Enum<?>> E getEnum(String attributeName){
        return (E) this.doGet(attributeName,Enum.class);
    }

    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getClass(String attributeName){
        return this.doGet(attributeName,Class.class);
    }

    public Class<?>[] getClassArray(String attributeName){
        return this.doGet(attributeName,Class[].class);
    }

    private <T> T doGet(String attributeName,Class<T> expectedType){
        Object value = this.get(attributeName);
        Assert.notNull(value, String.format("Attribute '%s' is not found",attributeName));
        return (T) value;
    }

}
