package org.glimpseframework.internal.shader.parameters.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;
import org.glimpseframework.api.primitives.Angle;
import org.glimpseframework.api.primitives.Color;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedAttributeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ColorConverterTest {

	@Test(expected = UnsupportedAttributeException.class)
	public void testConvertAttribute() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT_VECTOR_3, "a_Color");
		Color color = Color.MAGENTA;
		ParameterConverter<Color> converter = new ColorConverter(adapter);
		// when:
		converter.convert(parameter, color);
		// then: UnsupportedAttributeException is thrown
	}

	@Test
	public void testConvertUniform3f() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.FLOAT_VECTOR_3, "u_Color");
		Color color = Color.MAGENTA;
		ParameterConverter<Color> converter = new ColorConverter(adapter);
		// when:
		converter.convert(parameter, color);
		// then:
		verify(adapter, times(1)).uniform("u_Color", 1.0f, 0.0f, 1.0f);
	}

	@Test
	public void testConvertUniform4f() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.FLOAT_VECTOR_4, "u_Color");
		Color color = Color.YELLOW;
		ParameterConverter<Color> converter = new ColorConverter(adapter);
		// when:
		converter.convert(parameter, color);
		// then:
		verify(adapter, times(1)).uniform("u_Color", 1.0f, 1.0f, 0.0f, 1.0f);
	}

	@Test
	public void testGetOutputTypes() throws Exception {
		// given:
		ParameterConverter<Color> converter = new ColorConverter(adapter);
		// when:
		Set<Parameter.Type> types = converter.getOutputTypes();
		// then:
		assertEquals(2, types.size());
		assertTrue(types.contains(Parameter.Type.FLOAT_VECTOR_3));
		assertTrue(types.contains(Parameter.Type.FLOAT_VECTOR_4));
	}

	@Mock
	private ShaderParameterAdapter adapter;
}
