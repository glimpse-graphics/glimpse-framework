package org.glimpseframework.internal.shader.parameters.converters;

import java.util.HashMap;
import java.util.Map;
import org.glimpseframework.api.primitives.Matrix;
import org.glimpseframework.api.primitives.vbo.VBO;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;

/**
 * Default GlimpseFramework converters for shader parameters.
 */
public class DefaultConverters {

	private DefaultConverters() {
	}

	/**
	 * Creates default GlimpseFramework converters for a given adapter.
	 * @param adapter adapter
	 * @return converters
	 */
	public static Map<Class<?>, ParameterConverter<?>> create(ShaderParameterAdapter adapter) {
		Map<Class<?>, ParameterConverter<?>> converters = new HashMap<Class<?>, ParameterConverter<?>>();

		converters.put(VBO.class, new VerticesConverter(adapter));

		converters.put(Matrix.class, new MatrixConverter(adapter));

		converters.put(Object.class, new UnsupportedConverter());

		return converters;
	}
}
