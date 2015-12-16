package org.glimpseframework.api;

/**
 * Common GlimpseFramework exception.
 * @author Slawomir Czerwinski
 */
public class GlimpseRuntimeException extends RuntimeException {

	/**
	 * Creates an exception
	 */
	public GlimpseRuntimeException() {
	}

	/**
	 * Creates an exception
	 * @param message exception message
	 */
	public GlimpseRuntimeException(String message) {
		super(message);
	}
}
