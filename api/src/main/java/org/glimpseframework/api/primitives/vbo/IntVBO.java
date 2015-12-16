package org.glimpseframework.api.primitives.vbo;

import java.nio.IntBuffer;

/**
 * Vertex buffer object containing integer numbers.
 *
 * <p>This class is immutable.</p>
 *
 * @author Slawomir Czerwinski
 */
public final class IntVBO implements VBO {

	/**
	 * Creates a new buffer of integer numbers.
	 * @param integers array of integer numbers
	 * @param vectorSize size of a vector of elements
	 */
	public IntVBO(int[] integers, int vectorSize) {
		this.integers = integers;
		this.vectorSize = vectorSize;
	}

	@Override
	public IntBuffer getBuffer() {
		return IntBuffer.wrap(integers);
	}

	@Override
	public int getLength() {
		return integers.length;
	}

	@Override
	public VBOType getType() {
		return VBOType.INT;
	}

	@Override
	public int getVectorSize() {
		return vectorSize;
	}

	@Override
	public String toString() {
		return String.format("VBO[%d vectors of %d integers]", integers.length / vectorSize, vectorSize);
	}

	private final int[] integers;
	private final int vectorSize;
}
