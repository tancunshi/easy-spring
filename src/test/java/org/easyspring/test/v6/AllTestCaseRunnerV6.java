package org.easyspring.test.v6;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JDKProxyTest.class,
        JdkAopProxyTest.class,
        BeanDefinitionReaderTest.class,
        ApplicationContextTest.class
})
public class AllTestCaseRunnerV6 {
}
