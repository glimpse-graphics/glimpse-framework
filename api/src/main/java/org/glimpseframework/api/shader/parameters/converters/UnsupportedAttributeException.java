package org.glimpseframework.api.shader.parameters.converters;

import org.glimpseframework.api.GlimpseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when shaders do not support attribute parameters of a given type.
 * @author Slawomir Czerwinski
 */
public class UnsupportedAttributeException extends GlimpseRuntimeException {

	/**
	 * Creates an exception
	 * @param type parameter type
	 */
	public UnsupportedAttributeException(Class<?> type) {
		super(String.format(MESSAGE_FORMAT, type != null ? type.getName() : null));
		LOG.error(getMessage(), this);
	}

	/**
	 * Creates an exception
	 * @param value parameter value
	 */
	public UnsupportedAttributeException(Object value) {
		this(value != null ? value.getClass() : null);
	}

	private static final String MESSAGE_FORMAT = "Shader attribute parameters not supported for type <%s>";

	private static final Logger LOG = LoggerFactory.getLogger(ParameterConverter.class);
}
