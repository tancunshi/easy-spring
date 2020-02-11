package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.BeansException;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.easyspring.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author tancunshi
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;
    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes =
            new LinkedHashSet<Class<? extends Annotation>>();
    public AutowiredAnnotationProcessor(){
        this.autowiredAnnotationTypes.add(Autowired.class);
        this.autowiredAnnotationTypes.add(Value.class);
    }

    public InjectionElement buildAutowiringMetadata(Class<?> clazz){
        Class<?> target = clazz;
        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();
        InjectionElementBuilder builder = new InjectionElementBuilder(beanFactory);
        while (target != null && target != Object.class){
            //Object 的super 为null
            for (Field field : target.getDeclaredFields()) {

                Annotation annotation = this.findAutowiredAnnotation(field);
                if (annotation != null && !Modifier.isStatic(field.getModifiers())) {
                    elements.add(builder.addAnnotation(annotation)
                            .addMemory(field)
                            .build()
                    );
                }
            }

            for (Method method : target.getDeclaredMethods()){

                Annotation annotation = this.findAutowiredAnnotation(method);
                if (annotation != null && !Modifier.isStatic(method.getModifiers())) {
                    elements.add(builder.addAnnotation(annotation)
                            .addMemory(method)
                            .build()
                    );
                }
            }
            target = target.getSuperclass();
        }

        return new InjectionMetadata(elements);
    }

    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionElement metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        }
        catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;
    }

    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
