package org.easyspring.test.v4;

import org.easyspring.core.io.Resource;
import org.easyspring.core.io.support.PackageResourceLoader;
import org.junit.Test;
import static junit.framework.TestCase.*;
import java.io.IOException;

public class LoadPackageTest {

    @Test
    public void packageClassToResource() throws Exception {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.easyspring.test.v1");
        assertEquals(resources.length, 3);
    }

    @Test
    public void packageNotExist() {
        PackageResourceLoader loader = new PackageResourceLoader();
        try {
            Resource[] resources = loader.getResources("org.notexist.beans");
            for (Resource resource : resources) {
                System.out.println(resource.getDescription());
            }
        } catch (Exception e) {
            return;
        }
        fail();
    }
}
