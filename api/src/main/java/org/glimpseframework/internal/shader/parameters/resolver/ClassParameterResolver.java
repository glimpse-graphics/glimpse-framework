package org.glimpseframework.internal.shader.parameters.resolver;

import java.lang.annotation.Annotation;

class ClassParameterResolver {

	public ClassParameterResolver register(Class<?> resolvedClass, Class<? extends Annotation> annotation) {
		fieldValueResolver.register(resolvedClass, annotation);
		methodValueResolver.register(resolvedClass, annotation);
		return this;
	}

	public Object resolve(Object object, String name) throws ReflectiveOperationException {
		Object result = fieldValueResolver.resolve(object, name);
		return result == null ? methodValueResolver.resolve(object, name) : result;
	}

	private FieldValueResolver fieldValueResolver = new FieldValueResolver();
	private MethodValueResolver methodValueResolver = new MethodValueResolver();
}
