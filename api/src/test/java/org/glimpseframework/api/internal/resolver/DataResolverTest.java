package org.glimpseframework.api.internal.resolver;

import org.glimpseframework.api.annotations.AnnotatedTestClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataResolverTest {

	@Before
	public void setUpDataResolver() {
		dataResolver = new DataResolver();
	}

	@Test
	public void testAttribFieldByName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findAttrib("aFloatField");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(1.1f, (Float) result, DELTA);
	}

	@Test
	public void testAttribFieldByNameOverridden() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findAttrib("aFloatFieldOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttribFieldByNameOverriddenName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findAttrib("overriddenFloatAttrib");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(2.2f, (Float) result, DELTA);
	}

	@Test
	public void testUniformFieldByName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findUniform("uFloatField");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(3.3f, (Float) result, DELTA);
	}

	@Test
	public void testUniformFieldByNameOverridden() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findUniform("uFloatFieldOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testUniformFieldByNameOverriddenName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findUniform("overriddenFloatUniform");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(4.4f, (Float) result, DELTA);
	}

	@Test
	public void testNotAnnotatedFieldAttribByName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findAttrib("notAnnotatedField");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testNotAnnotatedFieldUniformByName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findUniform("notAnnotatedField");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttribMethodByName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findAttrib("attribInt");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(11, ((Integer) result).intValue());
	}

	@Test
	public void testAttribMethodByNameOverridden() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findAttrib("attribIntOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttribMethodByNameOverriddenName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findAttrib("overriddenIntAttrib");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(22, ((Integer) result).intValue());
	}

	@Test
	public void testUniformMethodByName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findUniform("uniformInt");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(33, ((Integer) result).intValue());
	}

	@Test
	public void testUniformMethodByNameOverridden() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findUniform("uniformIntOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testUniformMethodByNameOverriddenName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findUniform("overriddenIntUniform");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(44, ((Integer) result).intValue());
	}

	@Test
	public void testNotAnnotatedMethodAttribByName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findAttrib("notAnnotatedMethod");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testNotAnnotatedMethodUniformByName() {
		// given:
		dataResolver.register(new AnnotatedTestClass());
		// when:
		Object result = dataResolver.findUniform("notAnnotatedMethod");
		// then:
		Assert.assertNull(result);
	}

	private static final float DELTA = 0.01f;

	private DataResolver dataResolver;
}
