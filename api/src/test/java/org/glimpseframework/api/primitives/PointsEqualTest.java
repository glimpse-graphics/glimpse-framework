package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PointsEqualTest {

	@Parameterized.Parameters(name = "{index}: {0} vs. {1} – {2}")
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{new Point(1.0f, 1.0f, 1.0f), new Point(1.0f, 1.0f, 1.0f), true},
				{Point.ORIGIN, Point.ORIGIN, true},
				{Point.ORIGIN, "ORIGIN", false},
				{new Point(1.0f, 1.0f, 1.0f), null, false},
				{new Point(1.0f, 1.0f, 1.0f), new Point(1.0001f, 1.0f, 1.0f), false},
				{new Point(1.0f, 1.0f, 1.0f), new Point(1.0f, 1.0001f, 1.0f), false},
				{new Point(1.0f, 1.0f, 1.0f), new Point(1.0f, 1.0f, 1.0001f), false},
		});
	}

	public PointsEqualTest(Point v1, Object v2, boolean expectedResult) {
		this.v1 = v1;
		this.v2 = v2;
		this.expectedResult = expectedResult;
	}

	@Test
	public void testEqual() {
		// given: v1, v2
		// when:
		boolean result = v1.equals(v2);
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	private Point v1;
	private Object v2;
	private boolean expectedResult;
}