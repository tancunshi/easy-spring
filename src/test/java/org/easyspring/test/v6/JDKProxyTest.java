package org.easyspring.test.v6;

import org.easyspring.test.aop.Animal;
import org.easyspring.test.aop.Bird;
import org.easyspring.util.ClassUtils;
import org.easyspring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxyTest {

    @Before
    public void clear(){
        MessageTracker.clear();
    }

    @Test
    public void birdProxyTest(){
        InvocationHandler handler = new InvocationHandlerImpl(new Bird());
        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        //这些接口的方法会被代理
        Class[] interfaces = Bird.class.getInterfaces();
        Animal bird = (Animal) Proxy.newProxyInstance(loader, interfaces, handler);
        bird.eat();
        Assert.assertEquals(Arrays.asList("jdk proxy","bird eat"),MessageTracker.getTracks());
    }

    public class InvocationHandlerImpl implements InvocationHandler{

        private Object targetObject;

        public InvocationHandlerImpl(Object targetObject){
            this.targetObject = targetObject;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MessageTracker.addTrack("jdk proxy");
            return method.invoke(this.targetObject,args);
        }
    }
}
