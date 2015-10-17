package org.glimpseframework.api.shader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when shader compilation fails.
 */
public class ShaderCompileException extends Exception {

	/**
	 * Creates an exception
	 * @param shaderInfoLog shader info log from GLES
	 */
	public ShaderCompileException(String shaderInfoLog) {
		super(String.format(MESSAGE_FORMAT, shaderInfoLog));
		LOG.error(getMessage(), this);
	}

	private static final String MESSAGE_FORMAT = "Shader compilation error:\n%s";

	private static final Logger LOG = LoggerFactory.getLogger(Shader.class);
}
