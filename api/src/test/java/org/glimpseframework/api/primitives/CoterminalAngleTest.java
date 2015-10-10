package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CoterminalAngleTest {

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{Angle.fromDegrees(0.0f), Angle.fromDegrees(0.0f), Angle.Direction.CLOCKWISE, Angle.fromDegrees(-360.0f)},
				{Angle.fromDegrees(0.0f), Angle.fromDegrees(0.0f), Angle.Direction.COUNTER_CLOCKWISE, Angle.fromDegrees(360.0f)},
				{Angle.fromDegrees(90.0f), Angle.fromDegrees(0.0f), Angle.Direction.CLOCKWISE, Angle.fromDegrees(-270.0f)},
				{Angle.fromDegrees(90.0f), Angle.fromDegrees(0.0f), Angle.Direction.COUNTER_CLOCKWISE, Angle.fromDegrees(90.0f)},
				{Angle.fromDegrees(-90.0f), Angle.fromDegrees(0.0f), Angle.Direction.CLOCKWISE, Angle.fromDegrees(-90.0f)},
				{Angle.fromDegrees(-90.0f), Angle.fromDegrees(0.0f), Angle.Direction.COUNTER_CLOCKWISE, Angle.fromDegrees(270.0f)},
				{Angle.fromDegrees(-180.0f), Angle.fromDegrees(720.0f), Angle.Direction.CLOCKWISE, Angle.fromDegrees(540.0f)},
				{Angle.fromDegrees(-180.0f), Angle.fromDegrees(720.0f), Angle.Direction.COUNTER_CLOCKWISE, Angle.fromDegrees(900.0f)},
				{Angle.fromDegrees(180.0f), Angle.fromDegrees(-720.0f), Angle.Direction.CLOCKWISE, Angle.fromDegrees(-900.0f)},
				{Angle.fromDegrees(180.0f), Angle.fromDegrees(-720.0f), Angle.Direction.COUNTER_CLOCKWISE, Angle.fromDegrees(-540.0f)},
		});
	}

	public CoterminalAngleTest(Angle angle, Angle reference, Angle.Direction direction, Angle expectedResult) {
		this.angle = angle;
		this.reference = reference;
		this.direction = direction;
		this.expectedResult = expectedResult;
	}

	@Test
	public void testGetCoterminalNextTo() {
		// given: angle, reference, direction
		// when:
		Angle result = angle.getCoterminalNextTo(reference, direction);
		// then:
		Assert.assertEquals(expectedResult.getDegrees(), result.getDegrees(), DELTA);
	}

	private Angle angle;
	private Angle reference;
	private Angle.Direction direction;
	private Angle expectedResult;

	public static final float DELTA = 0.0001f;
}
