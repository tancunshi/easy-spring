package org.easyspring.beans.factory;

import org.easyspring.beans.BeansException;

/**
 * @author tancunshi
 */
public class BeanCreationException extends BeansException {
    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
