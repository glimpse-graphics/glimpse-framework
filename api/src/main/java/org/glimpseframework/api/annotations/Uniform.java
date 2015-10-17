package org.glimpseframework.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Shader uniform parameter.
 *
 * <p>Uniform parameters are constant within a single model being rendered by a shader.</p>
 *
 * @author Slawomir Czerwinski
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface Uniform {

	/**
	 * Name of shader uniform variable.
	 * @return name of shader uniform variable
	 */
	String name() default "";
}
