package org.glimpseframework.api.internal.resolver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.glimpseframework.api.annotations.Attrib;
import org.glimpseframework.api.annotations.Uniform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolver of annotated fields and methods data.
 * @author Slawomir Czerwinski
 */
public class DataResolver {

	/**
	 * Registers an object for resolving data.
	 * @param object object containing data to be resolved
	 */
	public void register(Object object) {
		Class<?> objectClass = object.getClass();
		if (!attribResolvers.containsKey(objectClass)) {
			attribResolvers.put(objectClass, new ClassDataResolver().register(objectClass, Attrib.class));
		}
		if (!uniformResolvers.containsKey(objectClass)) {
			uniformResolvers.put(objectClass, new ClassDataResolver().register(objectClass, Uniform.class));
		}
		objects.add(object);
	}

	/**
	 * Unregisters an object.
	 * @param object object containing data to be resolved
	 */
	public void unregister(Object object) {
		objects.remove(object);
	}

	/**
	 * Resolves {@link org.glimpseframework.api.annotations.Attrib Attrib} data.
	 * @param name data name
	 * @return data value
	 */
	public Object resolveAttrib(String name) {
		return resolve(name, attribResolvers);
	}

	private Object resolve(String name, Map<Class<?>, ClassDataResolver> resolvers) {
		for (Object object : objects) {
			try {
				Object result = resolvers.get(object.getClass()).resolve(object, name);
				if (result != null) {
					return result;
				}
			} catch (ReflectiveOperationException e) {
				LOG.error("Error while resolving data", e);
			}
		}
		return null;
	}

	/**
	 * Resolves {@link org.glimpseframework.api.annotations.Uniform Uniform} data.
	 * @param name data name
	 * @return data value
	 */
	public Object resolveUniform(String name) {
		return resolve(name, uniformResolvers);
	}

	private static final Logger LOG = LoggerFactory.getLogger(DataResolver.class);

	private List<Object> objects = new LinkedList<Object>();

	private Map<Class<?>, ClassDataResolver> attribResolvers = new HashMap<Class<?>, ClassDataResolver>();
	private Map<Class<?>, ClassDataResolver> uniformResolvers = new HashMap<Class<?>, ClassDataResolver>();
}
