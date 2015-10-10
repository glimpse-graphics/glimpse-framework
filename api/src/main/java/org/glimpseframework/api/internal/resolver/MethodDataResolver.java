package org.glimpseframework.api.internal.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class MethodDataResolver extends AccessibleObjectDataResolver<Method> {

	@Override
	public MethodDataResolver register(Class<?> resolvedClass, Class<? extends Annotation> annotation) {
		for (Method method : resolvedClass.getDeclaredMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				registerMethod(method, method.getAnnotation(annotation));
			}
		}
		return this;
	}

	public void registerMethod(Method method, Annotation annotation) {
		method.setAccessible(true);
		String name = getNameFromAnnotation(annotation);
		methods.put(name.isEmpty() ? convertMethodName(method.getName()) : name, method);
	}

	private String convertMethodName(String name) {
		if (name.startsWith(GETTER_PREFIX)) {
			return name.substring(GETTER_PREFIX_LENGTH, GETTER_PREFIX_LENGTH + 1).toLowerCase()
					.concat(name.substring(GETTER_PREFIX_LENGTH + 1));
		}
		return name;
	}

	@Override
	public Object resolve(Object object, String name) throws ReflectiveOperationException {
		Method method = methods.get(name);
		return (method == null) ? null : method.invoke(object);
	}

	private static final String GETTER_PREFIX = "get";
	private static final int GETTER_PREFIX_LENGTH = GETTER_PREFIX.length();

	private Map<String, Method> methods = new HashMap<String, Method>();
}
