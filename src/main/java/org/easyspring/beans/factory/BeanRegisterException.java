package org.easyspring.beans.factory;

import org.easyspring.beans.BeansException;

/**
 * @author tancunshi
 */
public class BeanRegisterException extends BeansException {
    public BeanRegisterException(String msg) {
        super(msg);
    }

    public BeanRegisterException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
