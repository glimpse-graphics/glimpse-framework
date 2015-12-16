package org.glimpseframework.api.shader;

import org.glimpseframework.api.GlimpseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when shader program linking fails.
 */
public class ShaderProgramLinkException extends GlimpseException {

	/**
	 * Creates an exception
	 * @param programInfoLog program info log from GLES
	 */
	public ShaderProgramLinkException(String programInfoLog) {
		super(String.format(MESSAGE_FORMAT, programInfoLog));
		LOG.error(getMessage(), this);
	}

	private static final String MESSAGE_FORMAT = "Shader program linking error:\n%s";

	private static final Logger LOG = LoggerFactory.getLogger(ShaderProgram.class);
}
