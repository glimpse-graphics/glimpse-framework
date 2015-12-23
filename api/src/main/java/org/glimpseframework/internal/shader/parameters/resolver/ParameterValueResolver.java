package org.glimpseframework.internal.shader.parameters.resolver;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.glimpseframework.api.annotations.Attribute;
import org.glimpseframework.api.annotations.Uniform;

/**
 * Resolver of parameter values from annotated fields and methods.
 * @author Slawomir Czerwinski
 */
public class ParameterValueResolver {

	/**
	 * Creates a new parameter resolver.
	 */
	public ParameterValueResolver() {
		resolvers.put(Attribute.class, new HashMap<Class<?>, ClassParameterResolver>());
		resolvers.put(Uniform.class, new HashMap<Class<?>, ClassParameterResolver>());
	}

	/**
	 * Registers an object containing values of parameters.
	 * @param object object containing values of parameters
	 */
	public void register(Object object) {
		for (Class<? extends Annotation> annotation : resolvers.keySet()) {
			registerClass(object.getClass(), annotation);
		}
		objects.add(object);
	}

	private void registerClass(Class<?> objectClass, Class<? extends Annotation> annotation) {
		Map<Class<?>, ClassParameterResolver> annotationResolvers = resolvers.get(annotation);
		if (!annotationResolvers.containsKey(objectClass)) {
			annotationResolvers.put(objectClass, new ClassParameterResolver().register(objectClass, annotation));
		}
	}

	/**
	 * Unregisters an object containing values of parameters.
	 * @param object object containing values of parameters
	 */
	public void unregister(Object object) {
		objects.remove(object);
	}

	/**
	 * Resolves parameter value.
	 * @param annotation parameter scope annotation type
	 * @param name parameter name
	 * @return parameter value
	 * @throws ResolveParameterException when a reflection error occurs while resolving parameter value.
	 */
	public Object resolve(Class<? extends Annotation> annotation, String name) throws ResolveParameterException {
		return resolve(name, resolvers.get(annotation));
	}

	private Object resolve(String name, Map<Class<?>, ClassParameterResolver> resolvers)
			throws ResolveParameterException {
		for (Object object : objects) {
			try {
				Object result = resolvers.get(object.getClass()).resolve(object, name);
				if (result != null) {
					return result;
				}
			} catch (Exception e) {
				throw new ResolveParameterException(name, e);
			}
		}
		return null;
	}

	private List<Object> objects = new LinkedList<Object>();

	private Map<Class<? extends Annotation>, Map<Class<?>, ClassParameterResolver>> resolvers =
			new HashMap<Class<? extends Annotation>, Map<Class<?>, ClassParameterResolver>>();
}
