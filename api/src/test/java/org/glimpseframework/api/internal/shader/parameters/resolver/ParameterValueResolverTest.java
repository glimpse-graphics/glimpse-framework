package org.glimpseframework.api.internal.shader.parameters.resolver;

import org.glimpseframework.api.annotations.AnnotatedTestClass;
import org.glimpseframework.api.annotations.Attribute;
import org.glimpseframework.api.annotations.Uniform;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParameterValueResolverTest {

	@Before
	public void setUpParameterResolver() {
		parameterValueResolver = new ParameterValueResolver();
	}

	@Test
	public void testAttributeFieldByName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Attribute.class, "aFloatField");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(1.1f, (Float) result, DELTA);
	}

	@Test
	public void testAttributeFieldByNameOverridden() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Attribute.class, "aFloatFieldOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttributeFieldByNameOverriddenName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Attribute.class, "overriddenFloatAttrib");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(2.2f, (Float) result, DELTA);
	}

	@Test
	public void testUniformFieldByName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Uniform.class, "uFloatField");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(3.3f, (Float) result, DELTA);
	}

	@Test
	public void testUniformFieldByNameOverridden() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Uniform.class, "uFloatFieldOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testUniformFieldByNameOverriddenName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Uniform.class, "overriddenFloatUniform");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Float);
		Assert.assertEquals(4.4f, (Float) result, DELTA);
	}

	@Test
	public void testNotAnnotatedFieldAttributeByName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Attribute.class, "notAnnotatedField");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testNotAnnotatedFieldUniformByName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Uniform.class, "notAnnotatedField");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttributeMethodByName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Attribute.class, "attribInt");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(11, ((Integer) result).intValue());
	}

	@Test
	public void testAttributeMethodByNameOverridden() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Attribute.class, "attribIntOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testAttributeMethodByNameOverriddenName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Attribute.class, "overriddenIntAttrib");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(22, ((Integer) result).intValue());
	}

	@Test
	public void testUniformMethodByName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Uniform.class, "uniformInt");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(33, ((Integer) result).intValue());
	}

	@Test
	public void testUniformMethodByNameOverridden() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Uniform.class, "uniformIntOverridden");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testUniformMethodByNameOverriddenName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Uniform.class, "overriddenIntUniform");
		// then:
		Assert.assertNotNull(result);
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(44, ((Integer) result).intValue());
	}

	@Test
	public void testNotAnnotatedMethodAttributeByName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Attribute.class, "notAnnotatedMethod");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testNotAnnotatedMethodUniformByName() throws ResolveParameterException {
		// given:
		parameterValueResolver.register(new AnnotatedTestClass());
		// when:
		Object result = parameterValueResolver.resolve(Uniform.class, "notAnnotatedMethod");
		// then:
		Assert.assertNull(result);
	}

	@Test
	public void testUnregister() throws ResolveParameterException {
		// given:
		Object object = new AnnotatedTestClass();
		parameterValueResolver.register(object);
		// when:
		parameterValueResolver.unregister(object);
		// then:
		Assert.assertNull(parameterValueResolver.resolve(Attribute.class, "aFloatField"));
		Assert.assertNull(parameterValueResolver.resolve(Attribute.class, "overriddenFloatAttrib"));
		Assert.assertNull(parameterValueResolver.resolve(Uniform.class, "uFloatField"));
		Assert.assertNull(parameterValueResolver.resolve(Uniform.class, "overriddenFloatUniform"));
		Assert.assertNull(parameterValueResolver.resolve(Attribute.class, "attribInt"));
		Assert.assertNull(parameterValueResolver.resolve(Attribute.class, "overriddenIntAttrib"));
		Assert.assertNull(parameterValueResolver.resolve(Uniform.class, "uniformInt"));
		Assert.assertNull(parameterValueResolver.resolve(Uniform.class, "overriddenIntUniform"));
	}

	private static final float DELTA = 0.01f;

	private ParameterValueResolver parameterValueResolver;
}
