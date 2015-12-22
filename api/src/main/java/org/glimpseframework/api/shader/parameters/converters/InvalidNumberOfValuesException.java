package org.glimpseframework.api.shader.parameters.converters;

import org.glimpseframework.api.GlimpseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when invalid number of values is passed as a method parameter.
 */
public class InvalidNumberOfValuesException extends GlimpseRuntimeException {

	/**
	 * Creates an exception
	 * @param actual actual number of values
	 * @param min minimum number of values
	 * @param max maximum number of values
	 */
	public InvalidNumberOfValuesException(int actual, int min, int max) {
		super(String.format(MESSAGE_FORMAT, actual, min, max));
		LOG.error(getMessage(), this);
	}

	private static final String MESSAGE_FORMAT = "Invalid number of values: %d. Expected between %d\u2014%d values.";

	private static final Logger LOG = LoggerFactory.getLogger(ShaderParameterAdapter.class);
}
