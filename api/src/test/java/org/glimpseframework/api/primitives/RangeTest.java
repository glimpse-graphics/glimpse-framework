package org.glimpseframework.api.primitives;

import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RangeTest {

	@Before
	public void setUpLocale() {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	public void testRangeBasics() {
		// given:
		Range range = new Range(1.0f, 2.0f);
		// when:
		float begin = range.getBegin();
		float end = range.getEnd();
		// then:
		Assert.assertEquals(1.0f, begin, DELTA);
		Assert.assertEquals(2.0f, end, DELTA);
	}

	@Test
	public void testToString() {
		// given:
		Range range = new Range(1.0f, 2.0f);
		// when:
		String string = range.toString();
		// then:
		Assert.assertEquals("[1.0…2.0]", string);
	}

	@Test
	public void testHashCodeEqual() {
		// given:
		Range range1 = new Range(1.0f, 2.0f);
		Range range2 = new Range(1.0f, 2.0f);
		// when:
		int hash1 = range1.hashCode();
		int hash2 = range2.hashCode();
		// then:
		Assert.assertEquals(hash1, hash2);
	}

	@Test
	public void testHashCodeNotEqual() {
		// given:
		Range range1 = new Range(1.0f, 2.0f);
		Range range2 = new Range(1.0f, 2.001f);
		// when:
		int hash1 = range1.hashCode();
		int hash2 = range2.hashCode();
		// then:
		Assert.assertNotEquals(hash1, hash2);
	}

	public static final float DELTA = 0.0001f;
}
