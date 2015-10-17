package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ColorHashCodeTest {

	@Parameterized.Parameters(name = "{index}: {0} vs. {1} – {2}")
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{Color.BLACK, Color.BLACK, true},
				{Color.BLACK, Color.WHITE, false},
				{Color.BLACK, "BLACK", false},
				{new Color(0.5f, 0.5f, 0.5f), new Color(0.5f, 0.5f, 0.5f), true},
				{new Color(0.5f, 0.5f, 0.5f), new Color(0.51f, 0.5f, 0.5f), false},
				{new Color(0.5f, 0.5f, 0.5f), new Color(0.5f, 0.51f, 0.5f), false},
				{new Color(0.5f, 0.5f, 0.5f), new Color(0.5f, 0.5f, 0.51f), false},
				{new Color(0.5f, 0.5f, 0.5f), new Color(0.5f, 0.5f, 0.5f, 0.5f), false},
		});
	}

	public ColorHashCodeTest(Color color, Object object, boolean expectedResult) {
		this.color = color;
		this.object = object;
		this.expectedResult = expectedResult;
	}

	@Test
	public void testEqual() {
		// given: color, object
		// when:
		boolean result = color.hashCode() == object.hashCode();
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	private Color color;
	private Object object;
	private boolean expectedResult;

}
