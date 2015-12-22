package org.glimpseframework.api.primitives.vbo;

import java.nio.FloatBuffer;
import org.junit.Assert;
import org.junit.Test;

public class FloatVBOTest {

	@Test
	public void testGetBuffer() {
		// given:
		VBO vbo = new FloatVBO(FLOATS, 3);
		// when:
		FloatBuffer buffer = (FloatBuffer) vbo.getBuffer();
		// then:
		Assert.assertTrue(buffer.isDirect());
		Assert.assertEquals(FLOATS.length, buffer.capacity());
		buffer.rewind();
		for (float value : FLOATS) {
			Assert.assertEquals(value, buffer.get(), DELTA);
		}
	}

	@Test
	public void testGetLength() {
		// given:
		VBO vbo = new FloatVBO(FLOATS, 3);
		// when:
		int length = vbo.getLength();
		// then:
		Assert.assertEquals(12, length);
	}

	@Test
	public void testGetType() {
		// given:
		VBO vbo = new FloatVBO(FLOATS, 3);
		// when:
		VBO.VBOType type = vbo.getType();
		// then:
		Assert.assertEquals(VBO.VBOType.FLOAT, type);
	}

	@Test
	public void testGetElementBytes() {
		// given:
		VBO vbo = new FloatVBO(FLOATS, 3);
		// when:
		int elementBytes = vbo.getType().getElementBytes();
		// then:
		Assert.assertEquals(4, elementBytes);
	}

	@Test
	public void testGetVectorSize() {
		// given:
		VBO vbo = new FloatVBO(FLOATS, 3);
		// when:
		int vectorSize = vbo.getVectorSize();
		// then:
		Assert.assertEquals(3, vectorSize);
	}

	@Test
	public void testToString() {
		// given:
		VBO vbo = new FloatVBO(FLOATS, 3);
		// when:
		String string = vbo.toString();
		// then:
		Assert.assertEquals("VBO[4 vectors of 3 floats]", string);
	}

	private static final float[] FLOATS = { 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f };

	private static final float DELTA = 0.0001f;
}
