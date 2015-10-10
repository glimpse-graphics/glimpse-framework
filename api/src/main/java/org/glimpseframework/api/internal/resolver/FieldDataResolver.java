package org.glimpseframework.api.internal.resolver;

import java.lang.reflect.Field;

class FieldDataResolver extends AccessibleObjectDataResolver<Field> {

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
