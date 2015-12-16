package org.glimpseframework.internal.shader.parameters.resolver;

import org.glimpseframework.api.GlimpseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when a reflection error occurs while resolving parameter value.
 * @author Slawomir Czerwinski
 */
public class ResolveParameterException extends GlimpseException {

	/**
	 * Creates an exception.
	 * @param parameterName name of the parameter being resolved
	 * @param cause root exception
	 */
	public ResolveParameterException(String parameterName, Throwable cause) {
		super(String.format(MESSAGE_FORMAT, parameterName), cause);
		LOG.error(MESSAGE_FORMAT, this);
	}

	private static final String MESSAGE_FORMAT = "Error occurred while resolving value for: %s";

	private static final Logger LOG = LoggerFactory.getLogger(ParameterValueResolver.class);
}
