package org.glimpseframework.internal.shader.parameters.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;
import org.glimpseframework.api.primitives.Angle;
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
public class AngleConverterTest {

	@Test(expected = UnsupportedAttributeException.class)
	public void testConvertAttribute() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT, "a_Angle");
		Angle angle = Angle.fromRadians(0.3f);
		ParameterConverter<Angle> converter = new AngleConverter(adapter);
		// when:
		converter.convert(parameter, angle);
		// then: UnsupportedAttributeException is thrown
	}

	@Test
	public void testConvertUniform() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.FLOAT, "u_Angle");
		Angle angle = Angle.fromRadians(0.3f);
		ParameterConverter<Angle> converter = new AngleConverter(adapter);
		// when:
		converter.convert(parameter, angle);
		// then:
		verify(adapter, times(1)).uniform("u_Angle", 0.3f);
	}

	@Test
	public void testGetOutputTypes() throws Exception {
		// given:
		ParameterConverter<Angle> converter = new AngleConverter(adapter);
		// when:
		Set<Parameter.Type> types = converter.getOutputTypes();
		// then:
		assertEquals(1, types.size());
		assertEquals(Parameter.Type.FLOAT, types.iterator().next());
	}

	@Mock
	private ShaderParameterAdapter adapter;
}
