package org.glimpseframework.api.primitives.vbo;

import java.nio.IntBuffer;
import org.junit.Assert;
import org.junit.Test;

public class IntVBOTest {

	@Test
	public void testGetBuffer() {
		// given:
		VBO vbo = new IntVBO(INTEGERS, 3);
		// when:
		IntBuffer buffer = (IntBuffer) vbo.getBuffer();
		// then:
		Assert.assertTrue(buffer.isDirect());
		Assert.assertEquals(INTEGERS.length, buffer.capacity());
		buffer.rewind();
		for (int value : INTEGERS) {
			Assert.assertEquals(value, buffer.get());
		}
	}

	@Test
	public void testGetLength() {
		// given:
		VBO vbo = new IntVBO(INTEGERS, 3);
		// when:
		int length = vbo.getLength();
		// then:
		Assert.assertEquals(12, length);
	}

	@Test
	public void testGetType() {
		// given:
		VBO vbo = new IntVBO(INTEGERS, 3);
		// when:
		VBO.VBOType type = vbo.getType();
		// then:
		Assert.assertEquals(VBO.VBOType.INT, type);
	}

	@Test
	public void testGetElementBytes() {
		// given:
		VBO vbo = new IntVBO(INTEGERS, 3);
		// when:
		int elementBytes = vbo.getType().getElementBytes();
		// then:
		Assert.assertEquals(4, elementBytes);
	}

	@Test
	public void testGetVectorSize() {
		// given:
		VBO vbo = new IntVBO(INTEGERS, 3);
		// when:
		int vectorSize = vbo.getVectorSize();
		// then:
		Assert.assertEquals(3, vectorSize);
	}

	@Test
	public void testToString() {
		// given:
		VBO vbo = new IntVBO(INTEGERS, 3);
		// when:
		String string = vbo.toString();
		// then:
		Assert.assertEquals("VBO[4 vectors of 3 integers]", string);
	}

	private static final int[] INTEGERS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
}
