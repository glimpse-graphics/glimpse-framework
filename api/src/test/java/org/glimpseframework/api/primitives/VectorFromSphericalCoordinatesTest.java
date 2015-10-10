package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class VectorFromSphericalCoordinatesTest {

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{1.0f, Angle.fromDegrees(90.0f), Angle.fromDegrees(0.0f), Vector.X_UNIT_VECTOR},
				{1.0f, Angle.fromDegrees(90.0f), Angle.fromDegrees(90.0f), Vector.Y_UNIT_VECTOR},
				{1.0f, Angle.fromDegrees(0.0f), Angle.fromDegrees(0.0f), Vector.Z_UNIT_VECTOR},
				{0.0f, Angle.fromDegrees(0.0f), Angle.fromDegrees(0.0f), Vector.NULL_VECTOR},
				{1.73205f, Angle.fromDegrees(54.7356f), Angle.fromDegrees(45.0f), new Vector(1.0f, 1.0f, 1.0f)},
				{1.0f, Angle.fromDegrees(90.0f), Angle.fromDegrees(180.0f), new Vector(-1.0f, 0.0f, 0.0f)},
				{1.0f, Angle.fromDegrees(90.0f), Angle.fromDegrees(-90.0f), new Vector(0.0f, -1.0f, 0.0f)},
				{1.0f, Angle.fromDegrees(180.0f), Angle.fromDegrees(0.0f), new Vector(0.0f, 0.0f, -1.0f)},
		});
	}

	public VectorFromSphericalCoordinatesTest(
			float radius, Angle polarAngle, Angle azimuthAngle, Vector expectedVector) {
		this.radius = radius;
		this.polarAngle = polarAngle;
		this.azimuthAngle = azimuthAngle;
		this.expectedVector = expectedVector;
	}

	@Test
	public void testFromSphericalCoordinates() {
		// given: radius, polarAngle, azimuthAngle
		// when:
		Vector vector = Vector.fromSphericalCoordinates(radius, polarAngle, azimuthAngle);
		// then:
		assertEquals(expectedVector, vector);
	}

	private void assertEquals(Vector expected, Vector actual) {
		Assert.assertArrayEquals(expected.get3f(), actual.get3f(), DELTA);
	}

	private float radius;
	private Angle polarAngle;
	private Angle azimuthAngle;
	private Vector expectedVector;

	public static final float DELTA = 0.0001f;
}
