package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
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

    public abstract void inject(Object target);
}
