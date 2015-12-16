package org.glimpseframework.api.primitives.vbo;

import java.nio.ByteBuffer;

/**
 * Vertex buffer object containing bytes.
 *
 * <p>This class is immutable.</p>
 *
 * @author Slawomir Czerwinski
 */
public final class ByteVBO implements VBO {

	/**
	 * Creates a new buffer of bytes.
	 * @param bytes array of bytes
	 * @param vectorSize size of a vector of elements
	 */
	public ByteVBO(byte[] bytes, int vectorSize) {
		this.bytes = bytes;
		this.vectorSize = vectorSize;
	}

	@Override
	public ByteBuffer getBuffer() {
		return ByteBuffer.wrap(bytes);
	}

	@Override
	public int getLength() {
		return bytes.length;
	}

	@Override
	public VBOType getType() {
		return VBOType.BYTE;
	}

	@Override
	public int getVectorSize() {
		return vectorSize;
	}

	@Override
	public String toString() {
		return String.format("VBO[%d vectors of %d bytes]", bytes.length / vectorSize, vectorSize);
	}

	private final byte[] bytes;
	private final int vectorSize;
}
