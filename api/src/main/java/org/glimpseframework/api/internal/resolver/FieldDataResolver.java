package org.glimpseframework.api.internal.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class FieldDataResolver extends AccessibleObjectDataResolver<Field> {

	@Override
	public FieldDataResolver register(Class<?> resolvedClass, Class<? extends Annotation> annotation) {
		for (Field field : resolvedClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotation)) {
				registerField(field, field.getAnnotation(annotation));
			}
		}
		return this;
	}

	private void registerField(Field field, Annotation annotation) {
		field.setAccessible(true);
		String name = getNameFromAnnotation(annotation);
		fields.put(name.isEmpty() ? field.getName() : name, field);
	}

	@Override
	public Object resolve(Object object, String name) throws ReflectiveOperationException {
		Field field = fields.get(name);
		return (field == null) ? null : field.get(object);
	}

	private Map<String, Field> fields = new HashMap<String, Field>();
}
