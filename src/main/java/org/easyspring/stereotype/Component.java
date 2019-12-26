package org.easyspring.stereotype;

import java.lang.annotation.*;

/**
 * @author tancunshi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default  "";
}
