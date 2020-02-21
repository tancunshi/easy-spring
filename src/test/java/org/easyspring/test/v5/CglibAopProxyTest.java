package org.easyspring.test.v5;

import org.easyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.aop.aspectj.AspectJExpressionPointcut;
import org.easyspring.aop.config.AspectInstanceFactory;
import org.easyspring.aop.framework.AopConfig;
import org.easyspring.aop.framework.AopConfigSupport;
import org.easyspring.aop.framework.CglibProxyFactory;
import org.easyspring.test.aop.Controller;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class CglibAopProxyTest {
    private AspectJAfterReturningAdvice afterReturningAdvice = null;
    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJExpressionPointcut pointcut = null;
    private AspectInstanceFactory aspectInstanceFactory = new AspectInstanceFactory(){
        private TransactionManager manager = null;
        @Override
        public Object getAspectInstance() {
            if (manager == null){
                manager = new TransactionManager();
            }
            return manager;
        }
    };

    @Before
    public void init() throws Throwable{
        MessageTracker.clear();

        String expression = "execution(* org.easyspring.test.aop.*.sayHello(..))";
        pointcut = new AspectJExpressionPointcut(expression);
        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),
                aspectInstanceFactory,pointcut);
        afterReturningAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),
                aspectInstanceFactory,pointcut);
    }

    @Test
    public void testGetProxy(){
        AopConfig config = new AopConfigSupport();
        config.addAdvice(beforeAdvice);
        config.addAdvice(afterReturningAdvice);
        config.setTargetObject(new Controller());

        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);
        Controller controller = (Controller) proxyFactory.getProxy();
        controller.sayHello();
        Assert.assertEquals(Arrays.asList("start tx","hello","commit tx"), MessageTracker.getTracks());

        MessageTracker.clear();
        controller.toString();
        config.toString();
        Assert.assertEquals(Arrays.asList(), MessageTracker.getTracks());
    }
}
