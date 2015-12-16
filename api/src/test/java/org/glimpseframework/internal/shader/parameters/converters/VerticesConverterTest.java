package org.glimpseframework.internal.shader.parameters.converters;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Set;
import org.glimpseframework.api.primitives.vbo.FloatVBO;
import org.glimpseframework.api.primitives.vbo.IntVBO;
import org.glimpseframework.api.primitives.vbo.VBO;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedUniformException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerticesConverterTest {

	private class IsFloatBufferOfValues extends ArgumentMatcher<Buffer> {

		private float values[];

		public IsFloatBufferOfValues(float[] values) {
			this.values = values;
		}

		@Override
		public boolean matches(Object argument) {
			if (argument instanceof FloatBuffer) {
				FloatBuffer buffer = (FloatBuffer) argument;
				return Arrays.equals(values, buffer.array());
			}
			return false;
		}
	}

	private class IsIntBufferOfValues extends ArgumentMatcher<Buffer> {

		private int values[];

		public IsIntBufferOfValues(int[] values) {
			this.values = values;
		}

		@Override
		public boolean matches(Object argument) {
			if (argument instanceof IntBuffer) {
				IntBuffer buffer = (IntBuffer) argument;
				return Arrays.equals(values, buffer.array());
			}
			return false;
		}
	}

	@Test
	public void testConvertFloatAttribute() throws Exception {
		// given:
		Parameter parameter =
				new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT_VECTOR_3, "a_Position");
		VBO vbo = new FloatVBO(FLOAT_VALUES, 3);
		ParameterConverter<VBO> converter = new VerticesConverter(adapter);
		// when:
		converter.convert(parameter, vbo);
		// then:
		verify(adapter, times(1)).vertexAttributeBuffer(
				eq("a_Position"), eq(3), eq(VBO.VBOType.FLOAT), eq(false), eq(3 * Float.SIZE / 8),
				argThat(new IsFloatBufferOfValues(FLOAT_VALUES)));
	}

	@Test
	public void testConvertIntAttribute() throws Exception {
		// given:
		Parameter parameter =
				new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.INTEGER_VECTOR_2, "a_Coord");
		VBO vbo = new IntVBO(INT_VALUES, 2);
		ParameterConverter<VBO> converter = new VerticesConverter(adapter);
		// when:
		converter.convert(parameter, vbo);
		// then:
		verify(adapter, times(1)).vertexAttributeBuffer(
				eq("a_Coord"), eq(2), eq(VBO.VBOType.INT), eq(false), eq(2 * Integer.SIZE / 8),
				argThat(new IsIntBufferOfValues(INT_VALUES)));
	}

	@Test(expected = UnsupportedUniformException.class)
	public void testConvertUniform() throws Exception {
		// given:
		Parameter parameter =
				new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.FLOAT_VECTOR_3, "u_Position");
		VBO vbo = new FloatVBO(FLOAT_VALUES, 3);
		ParameterConverter<VBO> converter = new VerticesConverter(adapter);
		// when:
		converter.convert(parameter, vbo);
		// then: UnsupportedUniformException is thrown
	}

	@Test
	public void testGetOutputTypes() throws Exception {
		// given:
		ParameterConverter<VBO> converter = new VerticesConverter(adapter);
		// when:
		Set<Parameter.Type> types = converter.getOutputTypes();
		// then:
		assertTrue(types.contains(Parameter.Type.FLOAT_VECTOR_2));
		assertTrue(types.contains(Parameter.Type.FLOAT_VECTOR_3));
		assertTrue(types.contains(Parameter.Type.FLOAT_VECTOR_4));
		assertTrue(types.contains(Parameter.Type.INTEGER_VECTOR_2));
		assertTrue(types.contains(Parameter.Type.INTEGER_VECTOR_3));
		assertTrue(types.contains(Parameter.Type.INTEGER_VECTOR_4));
	}

	private static final float[] FLOAT_VALUES = {
			1.0f, 2.0f, 3.0f,
			4.0f, 5.0f, 6.0f,
			7.0f, 8.0f, 9.0f,
	};

	private static final int[] INT_VALUES = {
			1, 2, 3, 4, 5, 6
	};

	@Mock
	private ShaderParameterAdapter adapter;
}
