package org.easyspring.aop.framework;

/**
 * @author tancunshi
 */
public interface AopProxyFactory {
    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
