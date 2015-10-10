package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AnglesCompareTest {

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{Angle.fromDegrees(30.0f), Angle.fromDegrees(30.0f), 0},
				{Angle.RIGHT_ANGLE, Angle.RIGHT_ANGLE, 0},
				{Angle.RIGHT_ANGLE, Angle.FULL_ANGLE, -1},
				{Angle.STRAIGHT_ANGLE, Angle.RIGHT_ANGLE, 1},
				{Angle.fromDegrees(30.0f), Angle.fromDegrees(30.01f), -1},
				{Angle.fromDegrees(30.01f), Angle.fromDegrees(30.0f), 1},
		});
	}

	public AnglesCompareTest(Angle angle1, Angle angle2, int expectedResult) {
		this.angle1 = angle1;
		this.angle2 = angle2;
		this.expectedResult = expectedResult;
	}

	@Test
	public void testEqual() {
		// given: angle1, angle2
		// when:
		int result = angle1.compareTo(angle2);
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	private Angle angle1;
	private Angle angle2;
	private int expectedResult;

}
