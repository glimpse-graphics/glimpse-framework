package org.glimpseframework.api;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.Buffer;
import java.util.HashSet;
import java.util.Set;
import org.glimpseframework.api.annotations.Attribute;
import org.glimpseframework.api.annotations.Uniform;
import org.glimpseframework.api.models.Model;
import org.glimpseframework.api.primitives.Matrix;
import org.glimpseframework.api.primitives.vbo.FloatVBO;
import org.glimpseframework.api.primitives.vbo.VBO;
import org.glimpseframework.api.shader.ShaderProgram;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.test.matchers.IsFloatBufferOfValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SceneTest {

	private class SceneMock extends Scene {

		@Override
		protected ShaderParameterAdapter getAdapter(ShaderProgram shaderProgram) {
			return shaderParameterAdapter;
		}
	}

	private class TestValueObject {

		@Uniform
		private Matrix matrix = Matrix.IDENTITY_MATRIX;
	}

	private class TestModel implements Model {

		@Override
		public int getNumberOfVertices() {
			return 3;
		}

		@Override
		public ShaderProgram getShaderProgram() {
			return shaderProgram;
		}

		@Attribute
		private FloatVBO vertices = new FloatVBO(VERTICES, 3);
	}

	@Test
	public void testRenderNoModel() {
		// given:
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT_VECTOR_3, "vertices"));
		when(shaderProgram.getParameters()).thenReturn(parameters);
		Scene scene = new SceneMock();
		// when:
		scene.render();
		// then:
		verify(shaderProgram, never()).use();
		verify(shaderParameterAdapter, never()).vertexAttributeBuffer(
				anyString(), anyInt(), any(VBO.VBOType.class), anyBoolean(), anyInt(), any(Buffer.class));
		verify(shaderParameterAdapter, never()).drawTriangles(anyInt());
	}

	@Test
	public void testRenderSingleModel() {
		// given:
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT_VECTOR_3, "vertices"));
		when(shaderProgram.getParameters()).thenReturn(parameters);
		Scene scene = new SceneMock();
		// when:
		scene.put(new TestModel());
		scene.render();
		// then:
		verify(shaderProgram, times(1)).use();
		verify(shaderParameterAdapter, times(1)).vertexAttributeBuffer(
				eq("vertices"), eq(3), eq(VBO.VBOType.FLOAT), eq(false), eq(3 * Float.SIZE / 8),
				argThat(new IsFloatBufferOfValues(VERTICES)));
		verify(shaderParameterAdapter, times(1)).drawTriangles(3);
	}

	@Test
	public void testRenderSingleModelSingleValueObject() {
		// given:
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT_VECTOR_3, "vertices"));
		parameters.add(new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "matrix"));
		when(shaderProgram.getParameters()).thenReturn(parameters);
		Scene scene = new SceneMock();
		// when:
		scene.register(new TestValueObject());
		scene.put(new TestModel());
		scene.render();
		// then:
		verify(shaderProgram, times(1)).use();
		verify(shaderParameterAdapter, times(1)).vertexAttributeBuffer(
				eq("vertices"), eq(3), eq(VBO.VBOType.FLOAT), eq(false), eq(3 * Float.SIZE / 8),
				argThat(new IsFloatBufferOfValues(VERTICES)));
		verify(shaderParameterAdapter, times(1)).uniformMatrix4(
				"matrix", 1, false, Matrix.IDENTITY_MATRIX.get16f(), 0);
		verify(shaderParameterAdapter, times(1)).drawTriangles(3);
	}

	private static final float[] VERTICES = {
		1.1f, 2.2f, 3.3f,
		4.4f, 5.5f, 6.6f,
		7.7f, 8.8f, 9.9f,
	};

	@Mock
	private ShaderParameterAdapter shaderParameterAdapter;

	@Mock
	private ShaderProgram shaderProgram;
}
