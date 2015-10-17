package org.glimpseframework.api.primitives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullVectorNormalizationException extends RuntimeException {

	public NullVectorNormalizationException() {
		super(MESSAGE);
		LOG.error(MESSAGE, this);
	}

	private static final String MESSAGE = "Cannot normalize a null vector. Division by 0.";

	private static final Logger LOG = LoggerFactory.getLogger(Vector.class);
}
