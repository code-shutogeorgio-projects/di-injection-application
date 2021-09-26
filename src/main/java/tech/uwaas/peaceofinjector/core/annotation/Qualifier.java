package tech.uwaas.peaceofinjector.core.annotation;

import java.lang.annotation.*;

/**
 * @author SHUTO, Uwai
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}