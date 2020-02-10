package org.easyspring.beans.factory.config;

import org.easyspring.beans.factory.BeanFactory;

/**
 * @author tancunshi
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor descriptor);
}
