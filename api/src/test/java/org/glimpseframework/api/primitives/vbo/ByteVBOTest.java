package org.glimpseframework.api.primitives.vbo;

import java.nio.ByteBuffer;
import org.junit.Assert;
import org.junit.Test;

public class ByteVBOTest {

	@Test
	public void testGetBuffer() {
		// given:
		VBO vbo = new ByteVBO(BYTES, 3);
		// when:
		ByteBuffer buffer = (ByteBuffer) vbo.getBuffer();
		// then:
		Assert.assertArrayEquals(BYTES, buffer.array());
	}

	@Test
	public void testGetLength() {
		// given:
		VBO vbo = new ByteVBO(BYTES, 3);
		// when:
		int length = vbo.getLength();
		// then:
		Assert.assertEquals(12, length);
	}

	@Test
	public void testGetType() {
		// given:
		VBO vbo = new ByteVBO(BYTES, 3);
		// when:
		VBO.VBOType type = vbo.getType();
		// then:
		Assert.assertEquals(VBO.VBOType.BYTE, type);
	}

	@Test
	public void testGetElementBytes() {
		// given:
		VBO vbo = new ByteVBO(BYTES, 3);
		// when:
		int elementBytes = vbo.getType().getElementBytes();
		// then:
		Assert.assertEquals(1, elementBytes);
	}

	@Test
	public void testGetVectorSize() {
		// given:
		VBO vbo = new ByteVBO(BYTES, 3);
		// when:
		int vectorSize = vbo.getVectorSize();
		// then:
		Assert.assertEquals(3, vectorSize);
	}

	@Test
	public void testToString() {
		// given:
		VBO vbo = new ByteVBO(BYTES, 3);
		// when:
		String string = vbo.toString();
		// then:
		Assert.assertEquals("VBO[4 vectors of 3 bytes]", string);
	}

	private static final byte[] BYTES = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
}
