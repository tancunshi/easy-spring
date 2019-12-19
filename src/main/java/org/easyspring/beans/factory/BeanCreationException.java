package org.easyspring.beans.factory;

import org.easyspring.beans.BeanException;

/**
 *  Bean创建时出错抛出
 */
public class BeanCreationException extends BeanException {
    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
