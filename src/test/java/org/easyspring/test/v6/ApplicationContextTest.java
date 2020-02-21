package org.easyspring.test.v6;

import org.easyspring.aop.Pointcut;
import org.easyspring.aop.aspectj.AbstractAspectJAdvice;
import org.easyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.easyspring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.context.ApplicationContext;
import org.easyspring.context.support.ClassPathXmlApplicationContext;
import org.easyspring.tx.TransactionManager;
import org.junit.Test;
import java.util.function.Predicate;
import static org.junit.Assert.*;

public class ApplicationContextTest {

    private ApplicationContext context = null;
    private TransactionManager manager = null;
    private Pointcut pointcut = null;
    @Test
    public void beanDefinitionResolver(){
        context = new ClassPathXmlApplicationContext("petstore-v5.xml");
        manager = (TransactionManager) context.getBean("tx");
        assertNotNull(manager);

        pointcut = (Pointcut) context.getBean("pointcut");
        assertNotNull(pointcut);

        String beforeAdvice = AspectJBeforeAdvice.class.getName() + "#0";
        this.generatedTestCase((AbstractAspectJAdvice) context.getBean(beforeAdvice),
                methodName -> "start".equals(methodName));

        String afterReturningAdvice = AspectJAfterReturningAdvice.class.getName() + "#0";
        this.generatedTestCase((AbstractAspectJAdvice) context.getBean(afterReturningAdvice),
                methodName -> "commit".equals(methodName));

        String afterThrowingAdvice = AspectJAfterThrowingAdvice.class.getName() + "#0";
        this.generatedTestCase((AbstractAspectJAdvice) context.getBean(afterThrowingAdvice),
                methodName -> "rollback".equals(methodName));
    }

    public void generatedTestCase(AbstractAspectJAdvice advice, Predicate<String> predicate){
        assertNotNull(advice);
        assertNotNull(advice.getPointcut());
        assertEquals(advice.getPointcut().getExpression(),pointcut.getExpression());
        assertEquals(advice.getAdviceObject(), manager);
        assertTrue(predicate.test(advice.getAdviceMethod().getName()));
    }
}
