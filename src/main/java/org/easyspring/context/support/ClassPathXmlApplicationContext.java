package org.easyspring.context.support;

import org.easyspring.beans.factory.config.BeanPostProcessor;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.context.ApplicationContext;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;

import java.util.List;

/**
 * @author tancunshi
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext
        implements ApplicationContext {

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path, this.getClassLoader());
    }

}
