package org.glimpseframework.api.shader.parameters.converters;

import org.glimpseframework.api.GlimpseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when shaders do not support uniform parameters of a given type.
 */
public class UnsupportedUniformException extends GlimpseRuntimeException {

	/**
	 * Creates an exception
	 * @param type parameter type
	 */
	public UnsupportedUniformException(Class<?> type) {
		super(String.format(MESSAGE_FORMAT, type.getName()));
		LOG.error(getMessage(), this);
	}

	/**
	 * Creates an exception
	 * @param value parameter value
	 */
	public UnsupportedUniformException(Object value) {
		this(value.getClass());
	}

	private static final String MESSAGE_FORMAT = "Shader uniform parameters not supported for type <%s>";

	private static final Logger LOG = LoggerFactory.getLogger(ParameterConverter.class);
}
