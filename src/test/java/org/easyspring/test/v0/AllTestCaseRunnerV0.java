package org.easyspring.test.v0;

import org.easyspring.test.v1.AllTestCaseRunnerV1;
import org.easyspring.test.v2.AllTestCaseRunnerV2;
import org.easyspring.test.v3.AllTestCaseRunnerV3;
import org.easyspring.test.v4.AllTestCaseRunnerV4;
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
        AllTestCaseRunnerV4.class
})
public class AllTestCaseRunnerV0 {
}
