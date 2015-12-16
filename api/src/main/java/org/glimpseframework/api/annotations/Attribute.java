package org.glimpseframework.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Shader attribute parameter.
 *
 * <p>Attribute parameters are defined per vertex. They can only be read by a vertex shader.</p>
 *
 * @author Slawomir Czerwinski
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Documented
public @interface Attribute {

	/**
	 * Name of shader attribute variable.
	 * @return name of shader attribute variable
	 */
	String name() default "";
}
