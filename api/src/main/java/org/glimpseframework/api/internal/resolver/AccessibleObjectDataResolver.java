package org.glimpseframework.api.internal.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

abstract class AccessibleObjectDataResolver<E extends AccessibleObject> {

	public abstract AccessibleObjectDataResolver<E> register(
			Class<?> resolvedClass, Class<? extends Annotation> annotation);

	protected String getNameFromAnnotation(Annotation annotation) {
		try {
			return (String) annotation.getClass().getMethod("name").invoke(annotation);
		} catch (ReflectiveOperationException e) {
			return "";
		}
	}

	public abstract Object resolve(Object object, String name) throws ReflectiveOperationException;
}
