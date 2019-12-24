package org.easyspring.beans;

/**
 * @author spring source code
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value,Class<T> requiredType);
}
