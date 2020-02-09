package org.easyspring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author tancunshi
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    boolean required() default true;
    String value() default "";
}
