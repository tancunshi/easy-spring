package org.easyspring.test.v5;

import org.easyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.aop.aspectj.AspectJExpressionPointcut;
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
    private static AspectJAfterReturningAdvice afterReturningAdvice = null;
    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJExpressionPointcut pointcut = null;

    private TransactionManager tx;

    @Before
    public void setUp() throws Throwable{
        MessageTracker.clear();

        tx = new TransactionManager();
        String expression = "execution(* org.easyspring.test.aop.*.sayHello(..))";
        pointcut = new AspectJExpressionPointcut(expression);

        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),tx,pointcut);
        afterReturningAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),tx,pointcut);
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
    }
}
