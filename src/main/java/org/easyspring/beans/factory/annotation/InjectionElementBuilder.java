package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.AnnotationInjectionElementBuilder;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * @author tancunshi
 */
public class InjectionElementBuilder {
    protected Annotation annotation;
    private AutowireCapableBeanFactory factory;
    private Member member;
    private InjectionElement injectionElement;

    private InjectionElementBuilder(AutowireCapableBeanFactory factory){
        this.factory = factory;
    }

    public static InjectionElementBuilder builder(AutowireCapableBeanFactory factory){
        return new InjectionElementBuilder(factory);
    }

    public AnnotationInjectionElementBuilder addAnnotation(Annotation annotation){

        this.annotation = annotation;

        if (annotation instanceof Autowired){
            return new AutowiredInjectionElementBuilder();
        }
        if (annotation instanceof Value){
            return new ValueInjectionElementBuilder();
        }

        throw new RuntimeException("not support the annotation");
    }

    private class AutowiredInjectionElementBuilder implements AnnotationInjectionElementBuilder{

        public AutowiredInjectionElementBuilder addMemory(Member m){
            member = m;
            if (m instanceof Field){
                injectionElement = new AutowiredFieldElement(member,factory,annotation);
                return this;
            }
            if (m instanceof Method){
                injectionElement = new AutowiredMethodElement(member,factory,annotation);
                return this;
            }

            throw new RuntimeException("not support the memory");
        }

        public InjectionElement build(){
            return injectionElement;
        }
    }

    private class ValueInjectionElementBuilder implements AnnotationInjectionElementBuilder{

        public ValueInjectionElementBuilder addMemory(Member m){
            member = m;
            if (m instanceof Field){
                injectionElement = new ValueInjectionFieldElement(member,factory,annotation);
                return this;
            }

            throw new RuntimeException("not support the memory");
        }

        public InjectionElement build(){
            return injectionElement;
        }
    }

}
