package org.glimpseframework.api.materials;

import org.glimpseframework.api.GlimpseException;

/**
 * Exception thrown when generating texture fails.
 * @author Slawomir Czerwinski
 */
public class TextureGeneratingException extends GlimpseException {

	/**
	 * Creates an exception.
	 * @param message exception message
	 */
	public TextureGeneratingException(String message) {
		super(message);
	}
}
