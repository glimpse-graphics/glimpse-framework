package org.glimpseframework.internal.shader.parameters.converters;

import java.util.HashMap;
import java.util.Map;
import org.glimpseframework.api.materials.AbstractTexture;
import org.glimpseframework.api.primitives.Angle;
import org.glimpseframework.api.primitives.Color;
import org.glimpseframework.api.primitives.Matrix;
import org.glimpseframework.api.primitives.Point;
import org.glimpseframework.api.primitives.Vector;
import org.glimpseframework.api.primitives.vbo.ByteVBO;
import org.glimpseframework.api.primitives.vbo.FloatVBO;
import org.glimpseframework.api.primitives.vbo.IntVBO;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;

/**
 * Default GlimpseFramework converters for shader parameters.
 * @author Slawomir Czerwinski
 */
public class DefaultConverters {

	private DefaultConverters() {
	}

	/**
	 * Creates default GlimpseFramework converters for a given adapter.
	 * @param adapter adapter
	 * @return converters
	 */
	public static Map<Class<?>, ParameterConverter<?>> forAdapter(ShaderParameterAdapter adapter) {
		Map<Class<?>, ParameterConverter<?>> converters = new HashMap<Class<?>, ParameterConverter<?>>();

		converters.put(FloatVBO.class, new VerticesConverter(adapter));
		converters.put(IntVBO.class, new VerticesConverter(adapter));
		converters.put(ByteVBO.class, new VerticesConverter(adapter));

		converters.put(Angle.class, new AngleConverter(adapter));
		converters.put(Color.class, new ColorConverter(adapter));
		converters.put(Matrix.class, new MatrixConverter(adapter));
		converters.put(Point.class, new PointConverter(adapter));
		converters.put(Vector.class, new VectorConverter(adapter));

		converters.put(AbstractTexture.class, new TextureConverter(adapter));

		converters.put(Object.class, new UnsupportedConverter());
		converters.put(null, new NullConverter());

		return converters;
	}
}
