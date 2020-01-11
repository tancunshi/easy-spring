package org.easyspring.context.support;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.core.io.FileSystemResource;
import org.easyspring.core.io.Resource;

/**
 * @author tancunshi
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext
        implements BeanFactory {

    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
