package org.glimpseframework.api.shader.parameters.converters;

import org.glimpseframework.api.GlimpseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when a value of invalid type is applied to shader parameter converter.
 * @author Slawomir Czerwinski
 */
public class InvalidParameterValueTypeException extends GlimpseRuntimeException {

	/**
	 * Creates an exception
	 *
	 * @param value parameter value
	 */
	public InvalidParameterValueTypeException(Object value) {
		super(String.format(MESSAGE_FORMAT, value.getClass().getName()));
		LOG.error(getMessage(), this);
	}

	private static final String MESSAGE_FORMAT = "Shader parameter type is invalid: <%s>";

	private static final Logger LOG = LoggerFactory.getLogger(ParameterConverter.class);
}
