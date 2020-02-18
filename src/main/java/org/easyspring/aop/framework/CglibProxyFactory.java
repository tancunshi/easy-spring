package org.easyspring.aop.framework;

import net.sf.cglib.proxy.*;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author tancunshi
 */
public class CglibProxyFactory implements AopProxyFactory{

    private final AopConfig config;

    public CglibProxyFactory(AopConfig config) {
        this.config = config;
    }

    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(config.getTargetClass());

        Callback[] callbacks = new Callback[]{new CGLibMethodInterceptor(), NoOp.INSTANCE};
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);

        return enhancer.create();
    }


    public Object getProxy(ClassLoader classLoader) {
        return null;
    }

    private class CGLibMethodInterceptor implements net.sf.cglib.proxy.MethodInterceptor {

        public Object intercept(Object proxyObject, Method targetMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
            List<MethodInterceptor> interceptors = config.getAdvice(targetMethod);
            //此处非常坑爹，需要明白cglib代理的调用流程，如果直接反射targetMethod会造成死循环
            Method proxyMethod = proxyObject.getClass().getDeclaredMethod(methodProxy.getSuperName());
            proxyMethod.setAccessible(true);
            ReflectionUtils.makeAccessible(proxyMethod);
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxyObject, proxyMethod, args, interceptors);
            return invocation.proceed();
        }
    }

    private class ProxyCallbackFilter implements CallbackFilter{

        public int accept(Method method) {
            //如果method不符合任意PointcutExpression，则不被代理
            if (config.getAdvice(method).size() > 0){
                return 0;
            }
            return 1;
        }
    }
}
