package org.glimpseframework.internal.shader.parameters;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import org.glimpseframework.api.annotations.Attribute;
import org.glimpseframework.api.annotations.Uniform;
import org.glimpseframework.api.primitives.Color;
import org.glimpseframework.api.primitives.Matrix;
import org.glimpseframework.api.primitives.vbo.FloatVBO;
import org.glimpseframework.api.primitives.vbo.VBO;
import org.glimpseframework.api.shader.ShaderProgram;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedAttributeException;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedUniformException;
import org.glimpseframework.internal.shader.parameters.converters.DefaultConverters;
import org.glimpseframework.test.matchers.IsFloatBufferOfValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParametersManagerTest {

	private class TestClass {

		public TestClass(Matrix mvpMatrix, Color color, float[] values) {
			this.mvpMatrix = mvpMatrix;
			this.color = color;
			this.position = new FloatVBO(values, 3);
		}

		@Uniform(name = "u_MVPMatrix")
		private Matrix mvpMatrix;

		@Uniform(name = "u_Color")
		private Color color;

		@Attribute(name = "a_Position")
		private VBO position;

		@Uniform(name = "u_Text")
		private String text = "some text";

		@Uniform(name = "u_Bool")
		private Boolean bool = true;
	}

	@Test
	public void testApplyParameters() throws Exception {
		// given:
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_MVPMatrix"));
		parameters.add(new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.FLOAT_VECTOR_4, "u_Color"));
		parameters.add(new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT_VECTOR_3, "a_Position"));
		when(shaderProgram.getParameters()).thenReturn(parameters);
		ParametersManager parametersManager = new ParametersManager();
		parametersManager.registerAllConverters(DefaultConverters.forAdapter(adapter));
		parametersManager.registerValueObject(new TestClass(Matrix.IDENTITY_MATRIX, Color.GREEN, FLOAT_VALUES));
		// when:
		parametersManager.applyParameters(shaderProgram);
		// then:
		verify(adapter, times(1)).uniformMatrix4("u_MVPMatrix", 1, false, Matrix.IDENTITY_MATRIX.get16f(), 0);
		verify(adapter, times(1)).uniform("u_Color", 0.0f, 1.0f, 0.0f, 1.0f);
		verify(adapter, times(1)).vertexAttributeBuffer(
				eq("a_Position"), eq(3), eq(VBO.VBOType.FLOAT), eq(false), eq(3 * Float.SIZE / 8),
				argThat(new IsFloatBufferOfValues(FLOAT_VALUES)));
	}

	@Test(expected = UnsupportedUniformException.class)
	public void testApplyUnsupportedParameter() throws Exception {
		// given:
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.INTEGER, "u_Text"));
		when(shaderProgram.getParameters()).thenReturn(parameters);
		ParametersManager parametersManager = new ParametersManager();
		parametersManager.registerAllConverters(DefaultConverters.forAdapter(adapter));
		parametersManager.registerValueObject(new TestClass(Matrix.IDENTITY_MATRIX, Color.GREEN, FLOAT_VALUES));
		// when:
		parametersManager.applyParameters(shaderProgram);
		// then: UnsupportedUniformException is thrown
	}

	@Test
	public void testCustomConverter() throws Exception {
		// given:
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.BOOLEAN, "u_Bool"));
		when(shaderProgram.getParameters()).thenReturn(parameters);
		when(customConverter.getOutputTypes()).thenReturn(EnumSet.of(Parameter.Type.BOOLEAN));
		ParametersManager parametersManager = new ParametersManager();
		parametersManager.registerConverter(Boolean.class, customConverter);
		parametersManager.registerValueObject(new TestClass(Matrix.IDENTITY_MATRIX, Color.GREEN, FLOAT_VALUES));
		// when:
		parametersManager.applyParameters(shaderProgram);
		// then:
		verify(customConverter, times(1)).convert(any(Parameter.class), any());
	}

	private static final float[] FLOAT_VALUES = {
			1.0f, 2.0f, 3.0f,
			4.0f, 5.0f, 6.0f,
			7.0f, 8.0f, 9.0f,
	};

	@Test(expected = UnsupportedUniformException.class)
	public void testApplyNonExistingUniformParameter() throws Exception {
		// given:
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.INTEGER, "u_NonExisting"));
		when(shaderProgram.getParameters()).thenReturn(parameters);
		ParametersManager parametersManager = new ParametersManager();
		parametersManager.registerAllConverters(DefaultConverters.forAdapter(adapter));
		parametersManager.registerValueObject(new TestClass(Matrix.IDENTITY_MATRIX, Color.GREEN, FLOAT_VALUES));
		// when:
		parametersManager.applyParameters(shaderProgram);
		// then: UnsupportedUniformException is thrown
	}

	@Test(expected = UnsupportedAttributeException.class)
	public void testApplyNonExistingAttributeParameter() throws Exception {
		// given:
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.INTEGER, "a_NonExisting"));
		when(shaderProgram.getParameters()).thenReturn(parameters);
		ParametersManager parametersManager = new ParametersManager();
		parametersManager.registerAllConverters(DefaultConverters.forAdapter(adapter));
		parametersManager.registerValueObject(new TestClass(Matrix.IDENTITY_MATRIX, Color.GREEN, FLOAT_VALUES));
		// when:
		parametersManager.applyParameters(shaderProgram);
		// then: UnsupportedAttributeException is thrown
	}

	@Mock
	private ShaderParameterAdapter adapter;

	@Mock
	private ShaderProgram shaderProgram;

	@Mock
	private ParameterConverter<Boolean> customConverter;
}
