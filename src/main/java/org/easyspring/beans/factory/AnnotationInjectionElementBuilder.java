package org.easyspring.beans.factory;

import org.easyspring.beans.factory.annotation.InjectionElement;
import java.lang.reflect.Member;

/**
 * @author tancunshi
 */
public interface AnnotationInjectionElementBuilder {
    AnnotationInjectionElementBuilder addMemory(Member m);
    InjectionElement build();
}
