package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class RangesEqualTest {

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{new Range(1.0f, 2.0f), new Range(1.0f, 2.0f), true},
				{CONSTANT_RANGE, CONSTANT_RANGE, true},
				{new Range(1.0f, 2.0f), "RANGE", false},
				{new Range(1.0f, 2.0f), null, false},
				{new Range(1.0f, 2.0f), new Range(1.0001f, 2.0f), false},
				{new Range(1.0f, 2.0f), new Range(1.0f, 2.0001f), false},
		});
	}

	public RangesEqualTest(Range range, Object object, boolean expectedResult) {
		this.range = range;
		this.object = object;
		this.expectedResult = expectedResult;
	}

	@Test
	public void testEqual() {
		// given: range, object
		// when:
		boolean result = range.equals(object);
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	private static final Range CONSTANT_RANGE = new Range(1.1f, 2.2f);

	private Range range;
	private Object object;
	private boolean expectedResult;
}