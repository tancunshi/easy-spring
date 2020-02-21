package org.easyspring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tancunshi
 */
public class ConstructorArgument {
    private final List<ValueHolder> argumentValues = new ArrayList<ValueHolder>();

    public ConstructorArgument() {
    }

    public List<ValueHolder> getArgumentValues() {
        return argumentValues;
    }

    public void addArgumentValue(Object value){
        this.argumentValues.add(new ValueHolder(value));
    }

    public void addArgumentValue(ValueHolder vh) {
        argumentValues.add(vh);
    }

    public int getArgumentCount() {
        return this.argumentValues.size();
    }

    public boolean isEmpty() {
        return this.argumentValues.isEmpty();
    }

    /**
     * value可以是RuntimeBeanReference,TypedStringValue。或者是其它类型，关键看parser逻辑
     */
    public static class ValueHolder {
        private Object value;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
