package org.easyspring.test.v5;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.*;
import org.easyspring.test.aop.Controller;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.ClassUtils;
import org.easyspring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;

public class CGLibTest {

    @Before
    public void clear(){
        MessageTracker.clear();
    }

    @Test
    public void testCallBack(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Controller.class);
        //添加拦截器
        enhancer.setCallback(new TransactionInterceptor());
        Controller controller = (Controller) enhancer.create();
        controller.sayHello();
        Assert.assertEquals(Arrays.asList("start tx","hello","commit tx"), MessageTracker.getTracks());
    }

    @Test
    public void testFilter(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Controller.class);

        Callback[] callbacks = new Callback[]{ new TransactionInterceptor(), NoOp.INSTANCE };
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);

        Controller controller = (Controller) enhancer.create();
        controller.toString();
        Assert.assertEquals(Arrays.asList(), MessageTracker.getTracks());

        controller.sayHello();
        Assert.assertEquals(Arrays.asList("start tx","hello","commit tx"), MessageTracker.getTracks());
    }

    private static class ProxyCallbackFilter implements CallbackFilter{

        public int accept(Method method) {
            if (method.getName().startsWith("say")){
                return 0;
            }
            else {
                return 1;
            }
        }
    }

    private static class TransactionInterceptor implements MethodInterceptor{
        TransactionManager tx = new TransactionManager();

        public Object intercept(Object proxyObject, Method superMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
            //proxyObject是代理类，superMethod是proxy父类的方法，args是参数，methodProxy可以用来调用proxy中的代理方法，代理方法会调用父类的方法。
            //调用过程：代理类sayHello->拦截器intercept->methodProxy invokeSuper->代理类CGLIB$sayHello$0->super.sayHello

            tx.start();
            Object result = methodProxy.invokeSuper(proxyObject,args);
            tx.commit();
            return result;
        }
    }
}
