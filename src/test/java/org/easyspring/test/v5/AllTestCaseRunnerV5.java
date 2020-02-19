package org.easyspring.test.v5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author tancunshi
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PointcutTest.class,
        MethodLocatingFactoryTest.class,
        ReflectiveMethodInvocationTest.class,
        CGLibTest.class,
        CglibAopProxyTest.class
})
public class AllTestCaseRunnerV5 {
}
