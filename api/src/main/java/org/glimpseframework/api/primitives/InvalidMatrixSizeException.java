package org.glimpseframework.api.primitives;

import org.glimpseframework.api.GlimpseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when the number of elements provided to a matrix is different than 16.
 * @author Slawomir Czerwinski
 */
public class InvalidMatrixSizeException extends GlimpseRuntimeException {

	/**
	 * Creates an exception.
	 * @param matrixSize actual number of elements provided to a matrix
	 */
	public InvalidMatrixSizeException(int matrixSize) {
		super(String.format(MESSAGE_FORMAT, matrixSize));
		LOG.error(getMessage(), this);
	}

	private static final String MESSAGE_FORMAT =
			"Matrices must consist of exactly 16 (4×4) elements. However, %d elements were provided.";

	private static final Logger LOG = LoggerFactory.getLogger(Matrix.class);
}
