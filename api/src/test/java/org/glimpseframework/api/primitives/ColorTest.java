package org.glimpseframework.api.primitives;

import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

	@Test
	public void testColorBasics() {
		// given:
		Color color = new Color(1.0f, 0.2f, 0.5f, 0.7f);
		// when:
		float red = color.getR();
		float green = color.getG();
		float blue = color.getB();
		float alpha = color.getAlpha();
		// then:
		Assert.assertEquals(1.0f, red, DELTA);
		Assert.assertEquals(0.2f, green, DELTA);
		Assert.assertEquals(0.5f, blue, DELTA);
		Assert.assertEquals(0.7f, alpha, DELTA);
	}

	@Test
	public void testColor3f() {
		// given:
		Color color = new Color(1.0f, 0.0f, 0.5f);
		// when:
		float[] color3f = color.get3f();
		// then:
		Assert.assertArrayEquals(new float[]{1.0f, 0.0f, 0.5f}, color3f, DELTA);
	}

	@Test
	public void testColor4f() {
		// given:
		Color color = new Color(1.0f, 0.0f, 0.5f);
		// when:
		float[] color4f = color.get4f();
		// then:
		Assert.assertArrayEquals(new float[]{1.0f, 0.0f, 0.5f, 1.0f}, color4f, DELTA);
	}

	@Test
	public void testTransparentColor4f() {
		// given:
		Color color = new Color(1.0f, 0.0f, 0.5f, 0.5f);
		// when:
		float[] color4f = color.get4f();
		// then:
		Assert.assertArrayEquals(new float[]{1.0f, 0.0f, 0.5f, 0.5f}, color4f, DELTA);
	}

	@Test
	public void testToString() {
		// given:
		Color color = new Color(1.0f, 0.2f, 0.8f, 0.6f);
		// when:
		String string = color.toString();
		// then:
		Assert.assertEquals("#99ff33cc", string);
	}

	public static final float DELTA = 0.004f;
}
