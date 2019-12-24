package org.easyspring.beans.factory;

import org.easyspring.beans.BeanException;

/**
 * @author tancunshi
 */
public class BeanDefinitionStoreException extends BeanException {
    public BeanDefinitionStoreException(String msg) {
        super(msg);
    }

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
