package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class VectorsEqualTest {

	@Parameterized.Parameters(name = "{index}: {0} vs. {1} – {2}")
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{new Vector(1.0f, 1.0f, 1.0f), new Vector(1.0f, 1.0f, 1.0f), true},
				{Vector.NULL_VECTOR, Vector.NULL_VECTOR, true},
				{new Vector(1.0f, 1.0f, 1.0f), new Vector(1.0001f, 1.0f, 1.0f), false},
				{new Vector(1.0f, 1.0f, 1.0f), new Vector(1.0f, 1.0001f, 1.0f), false},
				{new Vector(1.0f, 1.0f, 1.0f), new Vector(1.0f, 1.0f, 1.0001f), false},
				{new Vector(1.0f, 1.0f, 1.0f), new Point(1.0f, 1.0f, 1.0f), false},
				{new Vector(1.0f, 1.0f, 1.0f), null, false},
		});
	}

	public VectorsEqualTest(Vector v1, Object v2, boolean expectedResult) {
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

	@Test
	public void testHashCodesEqual() {
		// given: v1, v2
		// when:
		boolean result = (v2 instanceof Vector) && (v1.hashCode() == v2.hashCode());
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	private Vector v1;
	private Object v2;
	private boolean expectedResult;
}