package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class VectorSphericalCoordinatesTest {

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{Vector.X_UNIT_VECTOR, 1.0f, Angle.fromDegrees(90.0f), Angle.fromDegrees(0.0f)},
				{Vector.Y_UNIT_VECTOR, 1.0f, Angle.fromDegrees(90.0f), Angle.fromDegrees(90.0f)},
				{Vector.Z_UNIT_VECTOR, 1.0f, Angle.fromDegrees(0.0f), Angle.fromDegrees(0.0f)},
				{Vector.NULL_VECTOR, 0.0f, Angle.fromDegrees(0.0f), Angle.fromDegrees(0.0f)},
				{new Vector(1.0f, 1.0f, 1.0f), 1.73205f, Angle.fromDegrees(54.7356f), Angle.fromDegrees(45.0f)},
				{new Vector(-1.0f, 0.0f, 0.0f), 1.0f, Angle.fromDegrees(90.0f), Angle.fromDegrees(180.0f)},
				{new Vector(0.0f, -1.0f, 0.0f), 1.0f, Angle.fromDegrees(90.0f), Angle.fromDegrees(-90.0f)},
				{new Vector(0.0f, 0.0f, -1.0f), 1.0f, Angle.fromDegrees(180.0f), Angle.fromDegrees(0.0f)},
		});
	}

	public VectorSphericalCoordinatesTest(
			Vector vector, float expectedRadius, Angle expectedPolarAngle, Angle expectedAzimuthAngle) {
		this.vector = vector;
		this.expectedRadius = expectedRadius;
		this.expectedPolarAngle = expectedPolarAngle;
		this.expectedAzimuthAngle = expectedAzimuthAngle;
	}

	@Test
	public void testRadius() {
		// given: vector
		// when:
		float result = vector.getMagnitude();
		// then:
		Assert.assertEquals(expectedRadius, result, DELTA);
	}

	@Test
	public void testPolarAngle() {
		// given: vector
		// when:
		Angle result = vector.getPolarAngle();
		// then:
		Assert.assertEquals(expectedPolarAngle.getRadians(), result.getRadians(), DELTA);
	}

	@Test
	public void testAzimuthAngle() {
		// given: vector
		// when:
		Angle result = vector.getAzimuthAngle();
		// then:
		Assert.assertEquals(expectedAzimuthAngle.getRadians(), result.getRadians(), DELTA);
	}

	private Vector vector;
	private float expectedRadius;
	private Angle expectedPolarAngle;
	private Angle expectedAzimuthAngle;

	public static final float DELTA = 0.0001f;
}
