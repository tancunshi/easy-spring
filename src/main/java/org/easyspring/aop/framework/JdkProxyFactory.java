package org.easyspring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.Advice;
import org.easyspring.util.Assert;
import org.easyspring.util.ClassUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tancunshi
 */
public class JdkProxyFactory implements AopProxyFactory{

    private final AopConfig config;

    public JdkProxyFactory(AopConfig config){
        this.config = config;
    }

    public Object getProxy() {
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        return getProxy(classLoader);
    }

    public Object getProxy(ClassLoader classLoader) {
        Assert.notNull(classLoader,"classLoader must exist");
        Assert.notNull(this.config.getProxiedInterfaces(),"proxiedInterfaces must exist");
        Assert.arraySizeGreaterZero(this.config.getProxiedInterfaces().length,"target must implement interface");

        //使用jdk动态代理实现aop，要求targetObjective必须要有ProxiedInterfaces
        InvocationHandler handler = new DynamicAdvisedHandler(this.config);
        Class[] interfaces = this.config.getProxiedInterfaces();
        return Proxy.newProxyInstance(classLoader,interfaces,handler);
    }

    public class DynamicAdvisedHandler implements InvocationHandler {
        private final AopConfig config;

        public DynamicAdvisedHandler(AopConfig config){
            this.config = config;
        }

        public Object invoke(Object proxyObject, Method targetMethod, Object[] args) throws Throwable {
            List<Advice> chains = this.config.getAdvice(targetMethod);

            if (chains.isEmpty()){
                return targetMethod.invoke(this.config.getTargetObject(),args);
            }

            List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>(chains);
            MethodInvocation invocation = new ReflectiveMethodInvocation(this.config.getTargetObject(),
                    targetMethod, args, interceptors);
            return invocation.proceed();
        }
    }
}
