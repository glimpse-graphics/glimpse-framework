package org.glimpseframework.internal.shader.parameters.resolver;

import java.lang.reflect.Field;

class FieldValueResolver extends AccessibleObjectValueResolver<Field> {

	@Override
	protected Field[] getAccessibleObjects(Class<?> resolvedClass) {
		return resolvedClass.getDeclaredFields();
	}

	@Override
	protected String getName(Field field) {
		return field.getName();
	}

	@Override
	protected Object getValue(Field field, Object object) throws ReflectiveOperationException {
		return field.get(object);
	}
}
