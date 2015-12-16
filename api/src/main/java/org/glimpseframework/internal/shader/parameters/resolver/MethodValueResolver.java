package org.glimpseframework.internal.shader.parameters.resolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class MethodValueResolver extends AccessibleObjectValueResolver<Method> {

	@Override
	protected Method[] getAccessibleObjects(Class<?> resolvedClass) {
		return resolvedClass.getDeclaredMethods();
	}

	@Override
	protected String getName(Method method) {
		return convertMethodName(method.getName());
	}

	private String convertMethodName(String name) {
		if (name.startsWith(GETTER_PREFIX)) {
			return name.substring(GETTER_PREFIX_LENGTH, GETTER_PREFIX_LENGTH + 1).toLowerCase()
					.concat(name.substring(GETTER_PREFIX_LENGTH + 1));
		}
		return name;
	}

	@Override
	protected Object getValue(Method method, Object object) throws ReflectiveOperationException {
		return method.invoke(object);
	}

	private static final String GETTER_PREFIX = "get";
	private static final int GETTER_PREFIX_LENGTH = GETTER_PREFIX.length();

	private Map<String, Method> methods = new HashMap<String, Method>();
}
