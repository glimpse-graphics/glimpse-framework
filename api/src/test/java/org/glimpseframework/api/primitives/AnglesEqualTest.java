package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AnglesEqualTest {

	@Parameterized.Parameters(name = "{index}: {0} vs. {1} – {2}")
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{Angle.fromDegrees(30.0f), Angle.fromDegrees(30.0f), true},
				{Angle.RIGHT_ANGLE, Angle.RIGHT_ANGLE, true},
				{Angle.RIGHT_ANGLE, Angle.FULL_ANGLE, false},
				{Angle.RIGHT_ANGLE, Angle.STRAIGHT_ANGLE, false},
				{Angle.RIGHT_ANGLE, null, false},
				{Angle.RIGHT_ANGLE, "RIGHT_ANGLE", false},
				{Angle.fromDegrees(30.0f), Angle.fromDegrees(30.01f), false},
				{Angle.fromDegrees(30.01f), Angle.fromDegrees(30.0f), false},
		});
	}

	public AnglesEqualTest(Angle angle, Object object, boolean expectedResult) {
		this.angle = angle;
		this.object = object;
		this.expectedResult = expectedResult;
	}

	@Test
	public void testEqual() {
		// given: angle, object
		// when:
		boolean result = angle.equals(object);
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	private Angle angle;
	private Object object;
	private boolean expectedResult;

}
