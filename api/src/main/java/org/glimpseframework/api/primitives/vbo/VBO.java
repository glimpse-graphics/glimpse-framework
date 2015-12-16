package org.glimpseframework.api.primitives.vbo;

import java.nio.Buffer;

/**
 * Vertex buffer object.
 * @author Slawomir Czerwinski
 */
public interface VBO {

	/**
	 * Type of VBO elements.
	 */
	enum VBOType {
		BYTE(Byte.SIZE),
		INT(Integer.SIZE),
		FLOAT(Float.SIZE);

		VBOType(int elementBits) {
			elementBytes = elementBits / Byte.SIZE;
		}

		/**
		 * Gets size of a single element of VBO, in bytes.
		 * @return size of a single element
		 */
		public int getElementBytes() {
			return elementBytes;
		}

		private int elementBytes;
	}

	/**
	 * Gets buffer.
	 * @return buffer
	 */
	Buffer getBuffer();

	/**
	 * Gets buffer length.
	 * @return buffer length
	 */
	int getLength();

	/**
	 * Gets buffer type.
	 * @return buffer type
	 */
	VBOType getType();

	/**
	 * Gets the size of a single vector of elements handled by shader.
	 * <p>This method must return value of 1, 2, 3 or 4.</p>
	 * @return size of a single vector of elements
	 */
	int getVectorSize();
}
