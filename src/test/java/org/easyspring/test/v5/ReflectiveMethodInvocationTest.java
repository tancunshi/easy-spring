package org.easyspring.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.aspectj.*;
import org.easyspring.aop.config.AspectInstanceFactory;
import org.easyspring.aop.framework.ReflectiveMethodInvocation;
import org.easyspring.test.aop.Controller;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.MessageTracker;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;
import java.util.*;

import static junit.framework.TestCase.*;

public class ReflectiveMethodInvocationTest {
    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterAdvice afterAdvice = null;
    private AspectJAfterReturningAdvice afterReturningAdvice = null;
    private AspectJAfterThrowingAdvice afterThrowingAdvice = null;
    private AspectJAroundAdvice aroundAdvice = null;
    private Controller controller = null;
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
    public void setUp() throws Exception{
        MessageTracker.clear();
        controller = new Controller();

        beforeAdvice = new AspectJBeforeAdvice(
                TransactionManager.class.getMethod("start"),
                aspectInstanceFactory, null
        );

        afterAdvice = new AspectJAfterAdvice(
                TransactionManager.class.getMethod("after"),
                aspectInstanceFactory, null
        );

        afterReturningAdvice = new AspectJAfterReturningAdvice(
                TransactionManager.class.getMethod("commit"),
                aspectInstanceFactory, null
        );

        afterThrowingAdvice = new AspectJAfterThrowingAdvice(
                TransactionManager.class.getMethod("rollback"),
                aspectInstanceFactory, null
        );

        aroundAdvice = new AspectJAroundAdvice(
                TransactionManager.class.getMethod("around", MethodInvocation.class),
                aspectInstanceFactory, null
        );
    }

    @Test
    public void testAfterThrowing() throws Throwable{
        Method targetMethod = Controller.class.getMethod("sayHello");
        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
        interceptors.add(beforeAdvice);
        interceptors.add(aroundAdvice);
        interceptors.add(afterAdvice);
        interceptors.add(afterReturningAdvice);
        interceptors.add(afterThrowingAdvice);

        ReflectiveMethodInvocation methodInvocation =
                new ReflectiveMethodInvocation(controller,targetMethod,new Object[0],interceptors);

        try {
            methodInvocation.proceed();
            Collection<String> msgs = MessageTracker.getTracks();
            assertEquals(msgs, Arrays.asList("start tx", "around before tx", "hello", "commit tx", "after tx", "around after tx"));
        }
        catch (Exception t){
            Collection<String> msgs = MessageTracker.getTracks();
            assertEquals(msgs, Arrays.asList("start tx", "around before tx", "hello", "rollback tx", "after tx"));
        }
    }
}
