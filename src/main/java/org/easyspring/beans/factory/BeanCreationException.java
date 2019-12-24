package org.easyspring.beans.factory;

import org.easyspring.beans.BeanException;

/**
 * @author tancunshi
 */
public class BeanCreationException extends BeanException {
    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
