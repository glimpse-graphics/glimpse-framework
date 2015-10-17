package org.glimpseframework.api.internal.shader.parameters;

import org.glimpseframework.api.annotations.AnnotatedTestClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParameterResolverTest {

	@Before
	public void setUpParameterResolver() {
		parameterResolver = new ParameterResolver();
	}

	@Test
	public void testAttributeFieldByName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveAttribute("aFloatField");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(1.1f, (Float) result, DELTA);
	}

	@Test
	public void testAttributeFieldByNameOverridden() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveAttribute("aFloatFieldOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttributeFieldByNameOverriddenName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveAttribute("overriddenFloatAttrib");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(2.2f, (Float) result, DELTA);
	}

	@Test
	public void testUniformFieldByName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveUniform("uFloatField");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(3.3f, (Float) result, DELTA);
	}

	@Test
	public void testUniformFieldByNameOverridden() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveUniform("uFloatFieldOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testUniformFieldByNameOverriddenName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveUniform("overriddenFloatUniform");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(4.4f, (Float) result, DELTA);
	}

	@Test
	public void testNotAnnotatedFieldAttributeByName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveAttribute("notAnnotatedField");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testNotAnnotatedFieldUniformByName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveUniform("notAnnotatedField");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttributeMethodByName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveAttribute("attribInt");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(11, ((Integer) result).intValue());
	}

	@Test
	public void testAttributeMethodByNameOverridden() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveAttribute("attribIntOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttributeMethodByNameOverriddenName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveAttribute("overriddenIntAttrib");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(22, ((Integer) result).intValue());
	}

	@Test
	public void testUniformMethodByName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveUniform("uniformInt");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(33, ((Integer) result).intValue());
	}

	@Test
	public void testUniformMethodByNameOverridden() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveUniform("uniformIntOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testUniformMethodByNameOverriddenName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveUniform("overriddenIntUniform");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(44, ((Integer) result).intValue());
	}

	@Test
	public void testNotAnnotatedMethodAttributeByName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveAttribute("notAnnotatedMethod");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testNotAnnotatedMethodUniformByName() throws ResolveParameterException {
		// given:
		parameterResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterResolver.resolveUniform("notAnnotatedMethod");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testUnregister() throws ResolveParameterException {
		// given:
		Object object = new AnnotatedTestClass();
		parameterResolver.register(object);
		// when:
		parameterResolver.unregister(object);
		// then:
		Assert.assertNull(parameterResolver.resolveAttribute("aFloatField"));
		Assert.assertNull(parameterResolver.resolveAttribute("overriddenFloatAttrib"));
		Assert.assertNull(parameterResolver.resolveUniform("uFloatField"));
		Assert.assertNull(parameterResolver.resolveUniform("overriddenFloatUniform"));
		Assert.assertNull(parameterResolver.resolveAttribute("attribInt"));
		Assert.assertNull(parameterResolver.resolveAttribute("overriddenIntAttrib"));
		Assert.assertNull(parameterResolver.resolveUniform("uniformInt"));
		Assert.assertNull(parameterResolver.resolveUniform("overriddenIntUniform"));
	}

	private static final float DELTA = 0.01f;

	private ParameterResolver parameterResolver;
}
