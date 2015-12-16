package org.glimpseframework.internal.shader.parameters.converters;

import static org.junit.Assert.assertTrue;

import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedAttributeException;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedUniformException;
import org.junit.Test;

public class UnsupportedConverterTest {

	@Test(expected = UnsupportedAttributeException.class)
	public void testConvertAttribute() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT, "a_Value");
		String value = "text";
		ParameterConverter<Object> converter = new UnsupportedConverter();
		// when:
		converter.convert(parameter, value);
		// then: UnsupportedAttributeException is thrown
	}

	@Test(expected = UnsupportedUniformException.class)
	public void testConvertUniform() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.INTEGER, "u_Value");
		String value = "text";
		ParameterConverter<Object> converter = new UnsupportedConverter();
		// when:
		converter.convert(parameter, value);
		// then: UnsupportedUniformException is thrown
	}

	@Test
	public void testGetOutputTypes() throws Exception {
		// given:
		ParameterConverter<Object> converter = new UnsupportedConverter();
		// when:
		Set<Parameter.Type> types = converter.getOutputTypes();
		// then:
		assertTrue(types.isEmpty());
	}
}
