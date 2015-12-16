package org.glimpseframework.api.primitives.vbo;

import java.nio.FloatBuffer;

/**
 * Vertex buffer object containing floating point numbers.
 *
 * <p>This class is immutable.</p>
 *
 * @author Slawomir Czerwinski
 */
public final class FloatVBO implements VBO {

	/**
	 * Creates a new buffer of floating point numbers.
	 * @param floats array of floating point numbers
	 * @param vectorSize size of a vector of elements
	 */
	public FloatVBO(float[] floats, int vectorSize) {
		this.floats = floats;
		this.vectorSize = vectorSize;
	}

	@Override
	public FloatBuffer getBuffer() {
		return FloatBuffer.wrap(floats);
	}

	@Override
	public int getLength() {
		return floats.length;
	}

	@Override
	public VBOType getType() {
		return VBOType.FLOAT;
	}

	@Override
	public int getVectorSize() {
		return vectorSize;
	}

	@Override
	public String toString() {
		return String.format("VBO[%d vectors of %d floats]", floats.length / vectorSize, vectorSize);
	}

	private final float[] floats;
	private final int vectorSize;
}
