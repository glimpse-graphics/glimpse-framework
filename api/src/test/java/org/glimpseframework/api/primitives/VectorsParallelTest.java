package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class VectorsParallelTest {

	@Parameterized.Parameters(name = "{index}: {0} parallel to {1} – {2}")
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{new Vector(1.0f, 1.0f, 1.0f), new Vector(1.0f, 1.0f, 1.0f), true},
				{new Vector(1.0f, 1.0f, 1.0f), new Vector(-1.0f, -1.0f, -1.0f), true},
				{new Vector(1.0f, 1.0f, 1.0f), new Vector(2.0f, 2.0f, 2.0f), true},
				{new Vector(1.0f, 1.0f, 1.0f), new Vector(0.0f, 0.0f, 0.0f), true},
				{new Vector(0.0f, 1.0f, 1.0f), new Vector(0.0f, 2.0f, 1.0f), false},
				{new Vector(1.0f, 0.0f, 1.0f), new Vector(2.0f, 0.0f, -2.0f), false},
				{new Vector(1.0f, 1.0f, 0.0f), new Vector(1.0f, 2.0f, 0.0f), false},
				{new Vector(0.0f, 1.0f, 0.0f), new Vector(0.0f, 2.0f, 0.0f), true},
				{new Vector(1.0f, 0.0f, 0.0f), new Vector(2.0f, 0.0f, 0.0f), true},
				{new Vector(0.0f, 0.0f, 1.0f), new Vector(0.0f, 0.0f, 2.0f), true},
		});
	}

	public VectorsParallelTest(Vector v1, Vector v2, boolean expectedResult) {
		this.v1 = v1;
		this.v2 = v2;
		this.expectedResult = expectedResult;
	}

	@Test
	public void testParallel() {
		// given: v1, v2
		// when:
		boolean result = v1.isParallelTo(v2);
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	private Vector v1, v2;
	private boolean expectedResult;
}
