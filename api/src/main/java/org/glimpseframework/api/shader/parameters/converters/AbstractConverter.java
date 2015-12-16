package org.glimpseframework.api.shader.parameters.converters;

import org.glimpseframework.api.shader.parameters.Parameter;

/**
 * Template implementation of OpenGL shader parameter converter.
 * @param <T> input type
 * @author Slawomir Czerwinski
 */
public abstract class AbstractConverter<T> implements ParameterConverter<T> {

	protected ShaderParameterAdapter adapter;

	/**
	 * Creates a new converter
	 * @param adapter adapter
	 */
	public AbstractConverter(ShaderParameterAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public final void convert(Parameter parameter, T value) {
		switch (parameter.getScope()) {
			case ATTRIBUTE:
				convertAttribute(parameter, value);
				break;
			case UNIFORM:
				convertUniform(parameter, value);
				break;
			default:
				break;
		}
	}

	protected void convertAttribute(Parameter parameter, T value) {
		throw new UnsupportedAttributeException(value);
	}

	protected void convertUniform(Parameter parameter, T value) {
		throw new UnsupportedUniformException(value);
	}
}
