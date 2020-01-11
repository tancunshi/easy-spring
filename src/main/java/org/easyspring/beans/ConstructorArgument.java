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

    public void addArgumentValues(ValueHolder vh) {
        argumentValues.add(vh);
    }

    public int getArgumentCount() {
        return this.argumentValues.size();
    }

    public boolean isEmpty() {
        return this.argumentValues.isEmpty();
    }

    /**
     * 简易版构造器注入，constructor-arg暂时只支持value和ref
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
