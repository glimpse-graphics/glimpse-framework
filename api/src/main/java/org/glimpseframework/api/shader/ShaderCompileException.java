package org.glimpseframework.api.shader;

import org.glimpseframework.api.GlimpseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when shader compilation fails.
 * @author Slawomir Czerwinski
 */
public class ShaderCompileException extends GlimpseException {

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
