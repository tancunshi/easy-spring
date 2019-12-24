package org.easyspring.beans.factory;

import org.easyspring.beans.BeansException;

/**
 * @author tancunshi
 */
public class BeanDefinitionStoreException extends BeansException {
    public BeanDefinitionStoreException(String msg) {
        super(msg);
    }

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
