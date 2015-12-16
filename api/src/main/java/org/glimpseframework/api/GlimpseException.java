package org.glimpseframework.api;

/**
 * Common GlimpseFramework exception.
 */
public class GlimpseException extends Exception {

	/**
	 * Creates an exception
	 */
	public GlimpseException() {
	}

	/**
	 * Creates an exception
	 * @param message exception message
	 */
	public GlimpseException(String message) {
		super(message);
	}

	/**
	 * Creates an exception
	 * @param message exception message
	 * @param cause exception root cause
	 */
	public GlimpseException(String message, Throwable cause) {
		super(message, cause);
	}
}
