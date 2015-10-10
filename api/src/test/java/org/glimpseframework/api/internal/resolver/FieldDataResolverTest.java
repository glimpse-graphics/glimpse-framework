package org.glimpseframework.api.internal.resolver;

import org.glimpseframework.api.annotations.Attrib;
import org.glimpseframework.api.annotations.DataType;
import org.glimpseframework.api.annotations.Uniform;
import org.junit.Assert;
import org.junit.Test;

public class FieldDataResolverTest {

	private class TestClass {

		@Attrib(type = DataType.INTEGER)
		private int intValue = 17;

		@Attrib(name = "overriddenFloatValue", type = DataType.FLOAT)
		private float floatValue = 17.23f;

		@Uniform(type = DataType.BOOLEAN)
		private boolean notAnnotatedField = true;
	}

	@Test
	public void testResolveByFieldName() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		FieldDataResolver resolver = new FieldDataResolver().register(TestClass.class, Attrib.class);
		Object actual = resolver.resolve(testObject, "intValue");
		// then:
		Assert.assertNotNull(actual);
		Assert.assertTrue(actual instanceof Integer);
		Assert.assertEquals(17, ((Integer) actual).intValue());
	}

	@Test
	public void testResolveByOverriddenName() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		FieldDataResolver resolver = new FieldDataResolver().register(TestClass.class, Attrib.class);
		Object actual = resolver.resolve(testObject, "overriddenFloatValue");
		// then:
		Assert.assertNotNull(actual);
		Assert.assertTrue(actual instanceof Float);
		Assert.assertEquals(17.23f, (Float) actual, DELTA);
	}

	@Test
	public void testResolveOverriddenFieldByFieldName() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		FieldDataResolver resolver = new FieldDataResolver().register(TestClass.class, Attrib.class);
		Object actual = resolver.resolve(testObject, "floatValue");
		// then:
		Assert.assertNull(actual);
	}

	@Test
	public void testResolveNotAnnotatedField() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		FieldDataResolver resolver = new FieldDataResolver().register(TestClass.class, Attrib.class);
		Object actual = resolver.resolve(testObject, "notAnnotatedField");
		// then:
		Assert.assertNull(actual);
	}

	private static final float DELTA = 0.001f;
}
