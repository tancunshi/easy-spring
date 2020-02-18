package org.easyspring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.easyspring.aop.Advice;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author tancunshi
 */
public interface AopConfig {
    Class<?> getTargetClass();
    Object getTargetObject();
    boolean isProxyTargetClass();
    Class<?>[] getProxiedInterfaces();
    boolean isInterfaceProxied(Class<?> clazz);
    List<Advice> getAdvices();
    void addAdvice(Advice advice);
    List<Advice> getAdvice(Method method);
    void setTargetObject(Object object);
}
