package org.glimpseframework.api.internal.shader.parameters;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.glimpseframework.api.annotations.Attribute;
import org.glimpseframework.api.annotations.Uniform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolver of parameter values from annotated fields and methods.
 * @author Slawomir Czerwinski
 */
public class ParameterResolver {

	/**
	 * Registers an object containing values of parameters.
	 * @param object object containing values of parameters
	 */
	public void register(Object object) {
		Class<?> objectClass = object.getClass();
		if (!attributeResolvers.containsKey(objectClass)) {
			attributeResolvers.put(objectClass, new ClassParameterResolver().register(objectClass, Attribute.class));
		}
		if (!uniformResolvers.containsKey(objectClass)) {
			uniformResolvers.put(objectClass, new ClassParameterResolver().register(objectClass, Uniform.class));
		}
		objects.add(object);
	}

	/**
	 * Unregisters an object containing values of parameters.
	 * @param object object containing values of parameters
	 */
	public void unregister(Object object) {
		objects.remove(object);
	}

	/**
	 * Resolves {@link Attribute attribute} parameter value.
	 * @param name attribute parameter name
	 * @return attribute parameter value
	 * @throws ResolveParameterException when a reflection error occurs while resolving parameter value.
	 */
	public Object resolveAttribute(String name) throws ResolveParameterException {
		return resolve(name, attributeResolvers);
	}

	private Object resolve(String name, Map<Class<?>, ClassParameterResolver> resolvers)
			throws ResolveParameterException {
		for (Object object : objects) {
			try {
				Object result = resolvers.get(object.getClass()).resolve(object, name);
				if (result != null) {
					return result;
				}
			} catch (ReflectiveOperationException e) {
				throw new ResolveParameterException(name, e);
			}
		}
		return null;
	}

	/**
	 * Resolves {@link org.glimpseframework.api.annotations.Uniform uniform} parameter value.
	 * @param name uniform parameter name
	 * @return uniform parameter value
	 * @throws ResolveParameterException when a reflection error occurs while resolving parameter value.
	 */
	public Object resolveUniform(String name) throws ResolveParameterException {
		return resolve(name, uniformResolvers);
	}

	private static final Logger LOG = LoggerFactory.getLogger(ParameterResolver.class);

	private List<Object> objects = new LinkedList<Object>();

	private Map<Class<?>, ClassParameterResolver> attributeResolvers = new HashMap<Class<?>, ClassParameterResolver>();
	private Map<Class<?>, ClassParameterResolver> uniformResolvers = new HashMap<Class<?>, ClassParameterResolver>();
}
