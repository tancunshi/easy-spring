package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;

/**
 * @author tancunshi
 */
public abstract class AbstractAutowiredInjectionElement implements InjectionElement{

    protected Annotation annotation;
    protected Member member;
    protected AutowireCapableBeanFactory factory;

    AbstractAutowiredInjectionElement(Member member,AutowireCapableBeanFactory factory,Annotation annotation){
        this.member = member;
        this.factory = factory;
        this.annotation = annotation;
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
