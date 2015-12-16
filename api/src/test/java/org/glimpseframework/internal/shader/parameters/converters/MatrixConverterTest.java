package org.glimpseframework.internal.shader.parameters.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;
import org.glimpseframework.api.primitives.Matrix;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedAttributeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MatrixConverterTest {

	@Test(expected = UnsupportedAttributeException.class)
	public void testConvertAttribute() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.MATRIX_4, "a_MVPMatrix");
		Matrix matrix = Matrix.IDENTITY_MATRIX;
		ParameterConverter<Matrix> converter = new MatrixConverter(adapter);
		// when:
		converter.convert(parameter, matrix);
		// then: UnsupportedAttributeException is thrown
	}

	@Test
	public void testConvertUniform() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_MVPMatrix");
		Matrix matrix = Matrix.IDENTITY_MATRIX;
		ParameterConverter<Matrix> converter = new MatrixConverter(adapter);
		// when:
		converter.convert(parameter, matrix);
		// then:
		verify(adapter, times(1)).uniformMatrix4("u_MVPMatrix", 1, false, Matrix.IDENTITY_MATRIX.get16f(), 0);
	}

	@Test
	public void testGetOutputTypes() throws Exception {
		// given:
		ParameterConverter<Matrix> converter = new MatrixConverter(adapter);
		// when:
		Set<Parameter.Type> types = converter.getOutputTypes();
		// then:
		assertEquals(1, types.size());
		assertEquals(Parameter.Type.MATRIX_4, types.iterator().next());
	}

	@Mock
	private ShaderParameterAdapter adapter;
}
