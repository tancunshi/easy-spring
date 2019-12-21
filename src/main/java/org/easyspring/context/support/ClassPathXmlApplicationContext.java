package org.easyspring.context.support;

import org.easyspring.context.ApplicationContext;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext
        implements ApplicationContext{

    public ClassPathXmlApplicationContext(String configFile){
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path);
    }
}
