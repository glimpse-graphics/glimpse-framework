package org.glimpseframework.internal.shader.parameters;

import java.util.HashMap;
import java.util.Map;
import org.glimpseframework.api.shader.ShaderProgram;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.internal.shader.parameters.resolver.ParameterValueResolver;
import org.glimpseframework.internal.shader.parameters.resolver.ResolveParameterException;

/**
 * OpenGL shader parameters manager.
 * @author Slawomir Czerwinski
 */
public class ParametersManager {

	/**
	 * Registers a shader parameter converter.
	 * @param cls shader parameter value type
	 * @param converter shader parameter converter
	 * @param <T> shader parameter value type
	 */
	public <T> void registerConverter(Class<T> cls, ParameterConverter<T> converter) {
		converters.put(cls, converter);
	}

	/**
	 * Registers all shader parameter converters from a given map.
	 * @param converters shader parameter converters
	 */
	public void registerAllConverters(Map<Class<?>, ParameterConverter<?>> converters) {
		this.converters.putAll(converters);
	}

	/**
	 * Unregisters a shader parameter converter for a given parameter value type.
	 * @param cls shader parameter value type
	 */
	public void unregisterConverter(Class<?> cls) {
		converters.remove(cls);
	}

	/**
	 * Registers an object containing values of parameters.
	 * @param object object containing values of parameters
	 */
	public void registerValueObject(Object object) {
		resolver.register(object);
	}

	/**
	 * Unregisters an object containing values of parameters.
	 * @param object object containing values of parameters
	 */
	public void unregisterValueObject(Object object) {
		resolver.unregister(object);
	}

	/**
	 * Applies parameters to the given shader program.
	 * @param shaderProgram shader program
	 * @throws ResolveParameterException when an exception occurs while resolving parameter value.
	 */
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
