package org.glimpseframework.api.internal.resolver;

import java.lang.annotation.Annotation;

class ClassDataResolver {

	public ClassDataResolver register(Class<?> resolvedClass, Class<? extends Annotation> annotation) {
		fieldDataResolver.register(resolvedClass, annotation);
		methodDataResolver.register(resolvedClass, annotation);
		return this;
	}

	public Object resolve(Object object, String name) throws ReflectiveOperationException {
		Object result = fieldDataResolver.resolve(object, name);
		return result == null ? methodDataResolver.resolve(object, name) : result;
	}

	private FieldDataResolver fieldDataResolver = new FieldDataResolver();
	private MethodDataResolver methodDataResolver = new MethodDataResolver();
}
