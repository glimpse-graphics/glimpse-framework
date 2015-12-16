package org.glimpseframework.internal.shader.parameters;

import java.util.HashMap;
import java.util.Map;
import org.glimpseframework.api.shader.ShaderProgram;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.internal.shader.parameters.converters.DefaultConverters;
import org.glimpseframework.internal.shader.parameters.resolver.ParameterValueResolver;
import org.glimpseframework.internal.shader.parameters.resolver.ResolveParameterException;

/**
 * OpenGL shader parameters manager.
 */
public class ParametersManager {

	public ParametersManager(ShaderParameterAdapter adapter) {
		converters.putAll(DefaultConverters.forAdapter(adapter));
	}

	public <T> void registerConverter(Class<T> cls, ParameterConverter<T> converter) {
		converters.put(cls, converter);
	}

	public <T> void unregisterConverter(Class<T> cls) {
		converters.remove(cls);
	}

	public void registerValueObject(Object object) {
		resolver.register(object);
	}

	public void unregisterValueObject(Object object) {
		resolver.unregister(object);
	}

	public void applyParameters(ShaderProgram shaderProgram) throws ResolveParameterException {
		for (Parameter parameter : shaderProgram.getParameters()) {
			applyParameter(parameter);
		}
	}

	private void applyParameter(Parameter parameter) throws ResolveParameterException {
		Object rawValue = resolver.resolve(parameter.getScope().getAnnotation(), parameter.getName());
		ParameterConverter<?> converter = findConverter(rawValue);
		converter.convert(parameter, rawValue);
	}

	private ParameterConverter<?> findConverter(Object rawValue) {
		ParameterConverter<?> converter = null;
		for (Class<?> cls = rawValue.getClass(); converter == null; cls = cls.getSuperclass()) {
			converter = converters.get(cls);
		}
		return converter;
	}

	private Map<Class<?>, ParameterConverter<?>> converters = new HashMap<Class<?>, ParameterConverter<?>>();
	private ParameterValueResolver resolver = new ParameterValueResolver();
}
