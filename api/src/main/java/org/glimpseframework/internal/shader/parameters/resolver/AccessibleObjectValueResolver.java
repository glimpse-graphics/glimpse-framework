package org.glimpseframework.internal.shader.parameters.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.HashMap;
import java.util.Map;

abstract class AccessibleObjectValueResolver<E extends AccessibleObject> {

	public AccessibleObjectValueResolver<E> register(Class<?> resolvedClass, Class<? extends Annotation> annotation) {
		for (E accessibleObject : getAccessibleObjects(resolvedClass)) {
			if (accessibleObject.isAnnotationPresent(annotation)) {
				registerAccessibleObject(accessibleObject, accessibleObject.getAnnotation(annotation));
			}
		}
		return this;
	}

	protected abstract E[] getAccessibleObjects(Class<?> resolvedClass);

	private void registerAccessibleObject(E accessibleObject, Annotation annotation) {
		accessibleObject.setAccessible(true);
		String name = getNameFromAnnotation(annotation);
		accessibleObjects.put(name.isEmpty() ? getName(accessibleObject) : name, accessibleObject);
	}

	protected String getNameFromAnnotation(Annotation annotation) {
		try {
			return (String) annotation.getClass().getMethod("name").invoke(annotation);
		} catch (ReflectiveOperationException e) {
			return "";
		}
	}

	protected abstract String getName(E accessibleObject);

	public Object resolve(Object object, String name) throws ReflectiveOperationException {
		E accessibleObject = accessibleObjects.get(name);
		return (accessibleObject == null) ? null : getValue(accessibleObject, object);
	}

	protected abstract Object getValue(E accessibleObject, Object object) throws ReflectiveOperationException;

	private Map<String, E> accessibleObjects = new HashMap<String, E>();
}
