package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import java.lang.reflect.Member;

/**
 * @author tancunshi
 */
public abstract class AbstractInjectionElement implements InjectionElement{
    protected Member member;

    protected AutowireCapableBeanFactory factory;

    AbstractInjectionElement(Member member,AutowireCapableBeanFactory factory){
        this.member = member;
        this.factory = factory;
    }

    protected Object resolveDependency(DependencyDescriptor dependency){
        try {
            return factory.resolveDependency(dependency);
        }
        catch (BeanCreationException dependencyNotFoundException){
            if (dependency.isRequired()){
                throw dependencyNotFoundException;
            }
        }
        return null;
    }

    public abstract void inject(Object target);
}
