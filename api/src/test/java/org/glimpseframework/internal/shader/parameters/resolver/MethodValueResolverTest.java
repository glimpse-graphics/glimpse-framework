package org.glimpseframework.internal.shader.parameters.resolver;

import org.glimpseframework.api.annotations.Attribute;
import org.glimpseframework.api.annotations.Uniform;
import org.junit.Assert;
import org.junit.Test;

public class MethodValueResolverTest {

	private class TestClass {

		@Uniform
		private int internalInteger() {
			return 17;
		}

		@Uniform(name = "overriddenFloat")
		private float internalFloat() {
			return 43.71f;
		}

		private float floatValue() {
			return 27.13f;
		}

		@Uniform
		private float getFloatValue() {
			return 13.27f;
		}

		@Attribute
		private boolean notAnnotatedMethod() {
			return true;
		}
	}

	@Test
	public void testResolveByMethodName() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		AccessibleObjectValueResolver resolver = new MethodValueResolver().register(TestClass.class, Uniform.class);
		Object actual = resolver.resolve(testObject, "internalInteger");
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
		AccessibleObjectValueResolver resolver = new MethodValueResolver().register(TestClass.class, Uniform.class);
		Object actual = resolver.resolve(testObject, "overriddenFloat");
		// then:
		Assert.assertNotNull(actual);
		Assert.assertTrue(actual instanceof Float);
		Assert.assertEquals(43.71f, (Float) actual, DELTA);
	}

	@Test
	public void testResolveOverriddenFieldByMethodName() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		AccessibleObjectValueResolver resolver = new MethodValueResolver().register(TestClass.class, Uniform.class);
		Object actual = resolver.resolve(testObject, "internalFloat");
		// then:
		Assert.assertNull(actual);
	}

	@Test
	public void testResolveGetter() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		AccessibleObjectValueResolver resolver = new MethodValueResolver().register(TestClass.class, Uniform.class);
		Object actual = resolver.resolve(testObject, "floatValue");
		// then:
		Assert.assertNotNull(actual);
		Assert.assertTrue(actual instanceof Float);
		Assert.assertEquals(13.27f, (Float) actual, DELTA);
	}

	@Test
	public void testResolveNotAnnotatedMethod() throws Exception {
		// given:
		TestClass testObject = new TestClass();
		// when:
		AccessibleObjectValueResolver resolver = new MethodValueResolver().register(TestClass.class, Uniform.class);
		Object actual = resolver.resolve(testObject, "notAnnotatedMethod");
		// then:
		Assert.assertNull(actual);
	}

	private static final float DELTA = 0.001f;
}
