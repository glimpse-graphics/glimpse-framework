package org.glimpseframework.api.shader.parameters.converters;

import org.glimpseframework.api.shader.parameters.Parameter;

/**
 * Template implementation of OpenGL shader parameter converter.
 * @param <T> input type
 * @author Slawomir Czerwinski
 */
public abstract class AbstractConverter<T> implements ParameterConverter<T> {

	/**
	 * Creates a new converter
	 * @param adapter adapter
	 */
	public AbstractConverter(ShaderParameterAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public final void convert(Parameter parameter, Object value) {
		try {
			switch (parameter.getScope()) {
				case ATTRIBUTE:
					convertAttribute(parameter, (T) value);
					break;
				case UNIFORM:
					convertUniform(parameter, (T) value);
					break;
				default:
					break;
			}
		} catch (ClassCastException e) {
			throw new InvalidParameterValueTypeException(value);
		}
	}

	/**
	 * Converts attribute parameter value to a shader parameter.
	 * @param parameter parameter descriptor
	 * @param value parameter value
	 */
	protected void convertAttribute(Parameter parameter, T value) {
		throw new UnsupportedAttributeException(value);
	}

	/**
	 * Converts uniform parameter value to a shader parameter.
	 * @param parameter parameter descriptor
	 * @param value parameter value
	 */
	protected void convertUniform(Parameter parameter, T value) {
		throw new UnsupportedUniformException(value);
	}

	/**
	 * Shader parameter adapter that may be used to apply the parameter value to a shader program.
	 */
	protected ShaderParameterAdapter adapter;
}
