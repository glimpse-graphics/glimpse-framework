package org.glimpseframework.api.primitives;

import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PointTest {

	@Before
	public void setUpLocale() {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	public void testPointBasics() {
		// given:
		Point p = new Point(1.0f, 2.0f, 3.0f);
		// when:
		float x = p.getX();
		float y = p.getY();
		float z = p.getZ();
		// then:
		Assert.assertEquals(1.0f, x, DELTA);
		Assert.assertEquals(2.0f, y, DELTA);
		Assert.assertEquals(3.0f, z, DELTA);
	}

	@Test
	public void testPoint3f() {
		// given:
		Point p = new Point(10.0f, 20.0f, 30.0f);
		// when:
		float[] point3f = p.get3f();
		// then:
		Assert.assertArrayEquals(new float[]{10.0f, 20.0f, 30.0f}, point3f, DELTA);
	}

	@Test
	public void testPoint4f() {
		// given:
		Point p = new Point(10.0f, 20.0f, 30.0f);
		// when:
		float[] point4f = p.get4f();
		// then:
		Assert.assertArrayEquals(new float[]{10.0f, 20.0f, 30.0f, 1.0f}, point4f, DELTA);
	}

	@Test
	public void testPointTranslate() {
		// given:
		Point p = new Point(10.0f, 20.0f, 30.0f);
		Vector v = new Vector(5.0f, 6.0f, 7.0f);
		// when:
		Point translated = p.translate(v);
		// then:
		assertEquals(new Point(15.0f, 26.0f, 37.0f), translated);
	}

	private void assertEquals(Point expected, Point actual) {
		Assert.assertArrayEquals(expected.get3f(), actual.get3f(), DELTA);
	}

	@Test
	public void testToString() {
		// given:
		Point point = new Point(1.0f, 2.0f, 3.0f);
		// when:
		String string = point.toString();
		// then:
		Assert.assertEquals("(1.0 2.0 3.0 1.0)", string);
	}

	@Test
	public void testHashCodeEqual() {
		// given:
		Point p1 = new Point(1.0f, 2.0f, 3.0f);
		Point p2 = new Point(1.0f, 2.0f, 3.0f);
		// when:
		int hash1 = p1.hashCode();
		int hash2 = p2.hashCode();
		// then:
		Assert.assertEquals(hash1, hash2);
	}

	@Test
	public void testHashCodeNotEqual() {
		// given:
		Point p1 = new Point(1.0f, 2.0f, 3.0f);
		Point p2 = new Point(1.1f, 2.0f, 3.0f);
		// when:
		int hash1 = p1.hashCode();
		int hash2 = p2.hashCode();
		// then:
		Assert.assertNotEquals(hash1, hash2);
	}

	public static final float DELTA = 0.0001f;
}
