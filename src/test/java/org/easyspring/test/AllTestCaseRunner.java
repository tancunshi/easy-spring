package org.easyspring.test;

import org.easyspring.test.v1.AllTestCaseRunnerV1;
import org.easyspring.test.v2.AllTestCaseRunnerV2;
import org.easyspring.test.v3.AllTestCaseRunnerV3;
import org.easyspring.test.v4.AllTestCaseRunnerV4;
import org.easyspring.test.v5.AllTestCaseRunnerV5;
import org.easyspring.test.v6.AllTestCaseRunnerV6;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author tancunshi
 * 运行所有测试用例
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllTestCaseRunnerV1.class,
        AllTestCaseRunnerV2.class,
        AllTestCaseRunnerV3.class,
        AllTestCaseRunnerV4.class,
        AllTestCaseRunnerV5.class,
        AllTestCaseRunnerV6.class
})
public class AllTestCaseRunner {
}
