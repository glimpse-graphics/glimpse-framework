package org.glimpseframework.api.primitives;

import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VectorTest {

	@Before
	public void setUpLocale() {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	public void testVectorBasics() {
		// given:
		Vector v = new Vector(1.0f, 2.0f, 3.0f);
		// when:
		float x = v.getX();
		float y = v.getY();
		float z = v.getZ();
		// then:
		Assert.assertEquals(1.0f, x, DELTA);
		Assert.assertEquals(2.0f, y, DELTA);
		Assert.assertEquals(3.0f, z, DELTA);
	}

	@Test
	public void testCreateFrom2Points() {
		// given:
		Point p1 = new Point(1.0f, 2.0f, 3.0f);
		Point p2 = new Point(2.0f, 4.0f, 6.0f);
		// when:
		Vector v = new Vector(p1, p2);
		// then:
		assertEquals(new Vector(1.0f, 2.0f, 3.0f), v);
	}

	@Test
	public void testMagnitude() {
		// given:
		Vector v = new Vector(1.0f, 1.0f, 1.0f);
		// when:
		float magnitude = v.getMagnitude();
		// then:
		Assert.assertEquals(1.732f, magnitude, DELTA);
	}

	@Test
	public void testVector3f() {
		// given:
		Vector v = new Vector(10.0f, 20.0f, 30.0f);
		// when:
		float[] vector3f = v.get3f();
		// then:
		Assert.assertArrayEquals(new float[]{10.0f, 20.0f, 30.0f}, vector3f, DELTA);
	}

	@Test
	public void testVector4f() {
		// given:
		Vector v = new Vector(10.0f, 20.0f, 30.0f);
		// when:
		float[] vector4f = v.get4f();
		// then:
		Assert.assertArrayEquals(new float[]{10.0f, 20.0f, 30.0f, 1.0f}, vector4f, DELTA);
	}

	@Test
	public void testNormalize() {
		// given:
		Vector v = new Vector(10.0f, 20.0f, 30.0f);
		// when:
		Vector normalized = v.normalize();
		// then:
		Assert.assertEquals(1.0f, normalized.getMagnitude(), DELTA);
		Assert.assertTrue(normalized.isParallelTo(v));
	}

	@Test
	public void testNormalizeLength() {
		// given:
		Vector v = new Vector(10.0f, 20.0f, 30.0f);
		// when:
		Vector normalized = v.normalize(10.0f);
		// then:
		Assert.assertEquals(10.0f, normalized.getMagnitude(), DELTA);
		Assert.assertTrue(normalized.isParallelTo(v));
	}

	@Test(expected = NullVectorNormalizationException.class)
	public void testNormalizeNullVector() {
		// given:
		Vector v = new Vector(0.0f, 0.0f, 0.0f);
		// when:
		v.normalize();
	}

	@Test
	public void testCrossProduct() {
		// given:
		Vector v1 = Vector.X_UNIT_VECTOR;
		Vector v2 = Vector.Y_UNIT_VECTOR;
		// when:
		Vector product = v1.crossProduct(v2);
		// then:
		assertEquals(Vector.Z_UNIT_VECTOR, product);
	}

	@Test
	public void testToString() {
		// given:
		Vector vector = new Vector(1.0f, 2.0f, 3.0f);
		// when:
		String string = vector.toString();
		// then:
		Assert.assertEquals("[1.0 2.0 3.0 1.0]", string);
	}

	@Test
	public void testHashCodeEqual() {
		// given:
		Vector v1 = new Vector(1.0f, 2.0f, 3.0f);
		Vector v2 = new Vector(1.0f, 2.0f, 3.0f);
		// when:
		int hash1 = v1.hashCode();
		int hash2 = v2.hashCode();
		// then:
		Assert.assertEquals(hash1, hash2);
	}

	@Test
	public void testHashCodeNotEqual() {
		// given:
		Vector v1 = new Vector(1.0f, 2.0f, 3.0f);
		Vector v2 = new Vector(1.1f, 2.0f, 3.0f);
		// when:
		int hash1 = v1.hashCode();
		int hash2 = v2.hashCode();
		// then:
		Assert.assertNotEquals(hash1, hash2);
	}

	private void assertEquals(Vector expected, Vector actual) {
		Assert.assertArrayEquals(expected.get3f(), actual.get3f(), DELTA);
	}

	public static final float DELTA = 0.0001f;
}
