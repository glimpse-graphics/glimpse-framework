package org.glimpseframework.api.primitives;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import org.glimpseframework.test.FunctionalTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AngleTest {

	@Before
	public void setUpLocale() {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	public void testFromDegrees() {
		// given:
		float degrees = 45.0f;
		// when:
		Angle angle = Angle.fromDegrees(degrees);
		// then:
		Assert.assertEquals(45.0f, angle.getDegrees(), DELTA);
		Assert.assertEquals(0.7853f, angle.getRadians(), DELTA);
	}

	@Test
	public void testFromRadians() {
		// given:
		float radians = 2.0f;
		// when:
		Angle angle = Angle.fromRadians(radians);
		// then:
		Assert.assertEquals(2.0f, angle.getRadians(), DELTA);
		Assert.assertEquals(114.5916f, angle.getDegrees(), DELTA);
	}

	@Test
	public void testToString() {
		// given:
		Angle angle = Angle.fromDegrees(30.0f);
		// when:
		String string = angle.toString();
		// then:
		Assert.assertEquals("30.0°", string);
	}

	@Test
	@Category(FunctionalTest.class)
	public void testSortList() {
		// given:
		List<Angle> angles = Arrays.asList(
				Angle.fromDegrees(0.0f),
				Angle.fromDegrees(60.0f),
				Angle.fromDegrees(-30.0f),
				Angle.fromDegrees(30.0f));
		// when:
		Collections.sort(angles);
		// then:
		Assert.assertEquals(-30.0f, angles.get(0).getDegrees(), DELTA);
		Assert.assertEquals(0.0f, angles.get(1).getDegrees(), DELTA);
		Assert.assertEquals(30.0f, angles.get(2).getDegrees(), DELTA);
		Assert.assertEquals(60.0f, angles.get(3).getDegrees(), DELTA);
	}

	@Test
	@Category(FunctionalTest.class)
	public void testHashSet() {
		// given:
		List<Angle> angles = Arrays.asList(
				Angle.fromDegrees(0.0f),
				Angle.fromDegrees(30.0f),
				Angle.fromDegrees(30.0f),
				Angle.fromDegrees(60.0f));
		// when:
		HashSet<Angle> anglesSet = new HashSet<Angle>(angles);
		// then:
		Assert.assertEquals(3, anglesSet.size());
	}

	public static final float DELTA = 0.0001f;
}
