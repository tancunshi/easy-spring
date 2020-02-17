package org.easyspring.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.easyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.easyspring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.aop.framework.ReflectiveMethodInvocation;
import org.easyspring.test.aop.Controller;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.MessageTracker;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static junit.framework.TestCase.*;

public class ReflectiveMethodInvocationTest {
    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterReturningAdvice afterReturningAdvice = null;
    private AspectJAfterThrowingAdvice afterThrowingAdvice = null;
    private Controller controller = null;
    private TransactionManager tx;

    @Before
    public void setUp() throws Exception{
        controller = new Controller();
        tx = new TransactionManager();

        MessageTracker.clear();
        beforeAdvice = new AspectJBeforeAdvice(
                TransactionManager.class.getMethod("start"), tx, null
        );

        afterReturningAdvice = new AspectJAfterReturningAdvice(
                TransactionManager.class.getMethod("commit"), tx, null
        );

        afterThrowingAdvice = new AspectJAfterThrowingAdvice(
                TransactionManager.class.getMethod("rollback"), tx, null
        );
    }

    @Test
    public void testAfterThrowing() throws Throwable{
        Method targetMethod = Controller.class.getMethod("sayHello");
        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
        interceptors.add(afterThrowingAdvice);
        interceptors.add(beforeAdvice);

        ReflectiveMethodInvocation methodInvocation =
                new ReflectiveMethodInvocation(controller,targetMethod,new Object[0],interceptors);

        try {
            methodInvocation.proceed();
        }
        catch (Exception t){
            Collection<String> msgs = MessageTracker.getTracks();
            assertEquals(2,msgs.size());
            assertEquals(msgs, Arrays.asList("start tx","rollback tx"));
        }
    }
}
