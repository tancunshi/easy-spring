package org.easyspring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author tancunshi
 * BeanFactory实现相关测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class
})
public class AllTestCaseRunnerV1 {
}
