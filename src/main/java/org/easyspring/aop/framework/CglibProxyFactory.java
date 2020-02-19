package org.easyspring.aop.framework;

import net.sf.cglib.proxy.*;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.Advice;
import org.easyspring.aop.AopConfigException;
import org.easyspring.util.Assert;
import org.easyspring.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tancunshi
 */
public class CglibProxyFactory implements AopProxyFactory{

    private final int AOP_PROXY = 0;
    private final AopConfig config;

    public CglibProxyFactory(AopConfig config) {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0) {
            throw new AopConfigException("No advisors and no TargetSource specified");
        }
        this.config = config;
    }

    public Object getProxy() {
        return getProxy(null);
    }


    public Object getProxy(ClassLoader classLoader) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(config.getTargetClass());
        enhancer.setClassLoader(classLoader);
        //不对构造器拦截
        enhancer.setInterceptDuringConstruction(false);

        Callback[] callbacks = new Callback[]{new DynamicAdvisedInterceptor(this.config)};
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);

        return enhancer.create();
    }

    private class DynamicAdvisedInterceptor implements net.sf.cglib.proxy.MethodInterceptor {

        private final AopConfig config;

        public DynamicAdvisedInterceptor(AopConfig config){
            this.config = config;
        }

        public Object intercept(Object proxyObject, Method targetMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
            List<Advice> chains = this.config.getAdvice(targetMethod);
            Object targetObject = this.config.getTargetObject();

            if (chains.isEmpty()){
                return targetMethod.invoke(targetObject,args);
            }

            List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>(chains);

            //两种写法

            /*
            //第一种写法非常坑爹，需要明白cglib代理的调用流程，如果直接反射targetMethod会造成死循环
            Method proxyMethod = proxyObject.getClass().getDeclaredMethod(methodProxy.getSuperName());
            proxyMethod.setAccessible(true);
            ReflectionUtils.makeAccessible(proxyMethod);
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxyObject, proxyMethod, args, interceptors);
            */

            //第二种写法
            MethodInvocation invocation = new ReflectiveMethodInvocation(targetObject,targetMethod,args,interceptors);
            return invocation.proceed();
        }
    }

    private class ProxyCallbackFilter implements CallbackFilter{

        public int accept(Method method) {
            //代理所有的方法，但是如果该方法没有任何的Advice，则直接invoke该方法
            return AOP_PROXY;
        }
    }
}
