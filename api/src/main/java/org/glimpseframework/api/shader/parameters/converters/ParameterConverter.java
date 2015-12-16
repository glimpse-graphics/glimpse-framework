package org.glimpseframework.api.shader.parameters.converters;

import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;

/**
 * OpenGL shader parameter converter.
 * @param <T> input type
 * @author Slawomir Czerwinski
 */
public interface ParameterConverter<T> {

	/**
	 * Converts parameter value to a shader parameter.
	 * @param parameter parameter descriptor
	 * @param value parameter value
	 */
	void convert(Parameter parameter, T value);

	/**
	 * Provides a set of supported shader parameter types.
	 * @return supported shader parameter types
	 */
	Set<Parameter.Type> getOutputTypes();
}
