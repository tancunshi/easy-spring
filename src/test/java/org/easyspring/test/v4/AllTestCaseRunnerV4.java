package org.easyspring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 注解相关功能测试
 *
 * @author tancunshi
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//        ApplicationContextTest.class,
        LoadPackageTest.class,
        AnnotationAttributeTest.class
})
public class AllTestCaseRunnerV4 {

}
