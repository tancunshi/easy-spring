package org.easyspring.test.v6;

import org.easyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.aop.aspectj.AspectJExpressionPointcut;
import org.easyspring.aop.config.AspectInstanceFactory;
import org.easyspring.aop.framework.AopConfig;
import org.easyspring.aop.framework.AopConfigSupport;
import org.easyspring.aop.framework.JdkProxyFactory;
import org.easyspring.test.aop.Animal;
import org.easyspring.test.aop.Bird;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class JdkAopProxyTest {
    private static AspectJAfterReturningAdvice afterReturningAdvice = null;
    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJExpressionPointcut pointcut = null;
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

        String expression = "execution(* org.easyspring.test.aop.*.eat(..))";
        pointcut = new AspectJExpressionPointcut(expression);
        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),
                aspectInstanceFactory,pointcut);
        afterReturningAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),
                aspectInstanceFactory,pointcut);
    }

    @Test
    public void jdkProxy(){
        AopConfigSupport config = new AopConfigSupport();
        config.setTargetObject(new Bird());
        config.addAdvice(afterReturningAdvice);
        config.addAdvice(beforeAdvice);
        config.addInterfaces(Bird.class.getInterfaces());

        JdkProxyFactory factory = new JdkProxyFactory(config);
        Animal bird = (Animal) factory.getProxy();
        bird.eat();
        Assert.assertEquals(Arrays.asList("start tx","bird eat","commit tx"), MessageTracker.getTracks());
    }
}
