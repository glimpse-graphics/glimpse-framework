package org.glimpseframework.api.primitives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidMatrixSizeException extends RuntimeException {

	public InvalidMatrixSizeException(int matrixSize) {
		super(String.format(MESSAGE_FORMAT, matrixSize));
		LOG.error(getMessage(), this);
	}

	private static final String MESSAGE_FORMAT =
			"Matrices must consist of exactly 16 (4×4) elements. However, %d elements were provided.";

	private static final Logger LOG = LoggerFactory.getLogger(Matrix.class);
}
