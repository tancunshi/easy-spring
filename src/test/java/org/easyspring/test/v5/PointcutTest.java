package org.easyspring.test.v5;

import org.easyspring.aop.MethodMatcher;
import org.easyspring.aop.aspectj.AspectJExpressionPointcut;
import org.easyspring.test.aop.Controller;
import org.easyspring.test.aop.Service;
import org.junit.Test;
import java.lang.reflect.Method;
import static junit.framework.TestCase.*;

public class PointcutTest {
    @Test
    public void testPointcut() throws Exception{
        String expression = "execution(* org.easyspring.test.aop.*.sayHello(..))";
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        MethodMatcher matcher = pc.getMethodMatcher();
        Class<?> targetClass = Controller.class;
        Method method = targetClass.getMethod("sayHello");
        assertTrue(matcher.matches(method));

        targetClass = Service.class;
        method = targetClass.getMethod("doSomething");
        assertFalse(matcher.matches(method));
    }
}
