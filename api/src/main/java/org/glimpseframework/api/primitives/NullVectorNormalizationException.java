package org.glimpseframework.api.primitives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when a vector with a magnitude of 0 is being normalized.
 * @author Slawomir Czerwinski
 */
public class NullVectorNormalizationException extends RuntimeException {

	/**
	 * Creates an exception.
	 */
	public NullVectorNormalizationException() {
		super(MESSAGE);
		LOG.error(MESSAGE, this);
	}

	private static final String MESSAGE = "Cannot normalize a null vector. Division by 0.";

	private static final Logger LOG = LoggerFactory.getLogger(Vector.class);
}
