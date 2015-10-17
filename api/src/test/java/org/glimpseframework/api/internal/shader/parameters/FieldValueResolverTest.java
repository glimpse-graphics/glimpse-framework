package org.glimpseframework.api.internal.shader.parameters;

import org.glimpseframework.api.annotations.Attribute;
import org.glimpseframework.api.annotations.Uniform;
import org.junit.Assert;
import org.junit.Test;

public class FieldValueResolverTest {

	private class TestClass {

		@Attribute
		private int intValue = 17;

		@Attribute(name = "overriddenFloatValue")
		private float floatValue = 17.23f;

		@Uniform
		private boolean notAnnotatedField = true;
	}

	@Test
	public void testResolveByFieldName() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		AccessibleObjectValueResolver resolver = new FieldValueResolver().register(TestClass.class, Attribute.class);
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
		AccessibleObjectValueResolver resolver = new FieldValueResolver().register(TestClass.class, Attribute.class);
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
		AccessibleObjectValueResolver resolver = new FieldValueResolver().register(TestClass.class, Attribute.class);
		Object actual = resolver.resolve(testObject, "floatValue");
		// then:
		Assert.assertNull(actual);
	}

	@Test
	public void testResolveNotAnnotatedField() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		AccessibleObjectValueResolver resolver = new FieldValueResolver().register(TestClass.class, Attribute.class);
		Object actual = resolver.resolve(testObject, "notAnnotatedField");
		// then:
		Assert.assertNull(actual);
	}

	private static final float DELTA = 0.001f;
}
