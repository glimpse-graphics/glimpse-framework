package org.glimpseframework.api.shader.parameters.converters;

import java.util.Set;
import org.glimpseframework.api.primitives.Vector;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractConverterTest {

	private class MockConverter<T> extends AbstractConverter<T> {

		public MockConverter(ShaderParameterAdapter adapter) {
			super(adapter);
		}

		@Override
		public Set<Parameter.Type> getOutputTypes() {
			return null;
		}
	}

	@Test(expected = UnsupportedAttributeException.class)
	public void testConvertAttribute() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT, "a_Value");
		Float value = 1.0f;
		ParameterConverter<Float> converter = new MockConverter<Float>(adapter);
		// when:
		converter.convert(parameter, value);
		// then: UnsupportedAttributeException is thrown
	}

	@Test(expected = UnsupportedUniformException.class)
	public void testConvertUniform() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.INTEGER, "u_Value");
		Float value = 1.0f;
		ParameterConverter<Float> converter = new MockConverter<Float>(adapter);
		// when:
		converter.convert(parameter, value);
		// then: UnsupportedUniformException is thrown
	}

	@Test(expected = InvalidParameterValueTypeException.class)
	public void testConvertInvalidType() throws Exception {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.FLOAT, "a_Value");
		Object value = Vector.X_UNIT_VECTOR;
		ParameterConverter<String> converter = new MockConverter<String>(adapter) {
			@Override
			protected void convertAttribute(Parameter parameter, String value) {
				value = value.concat(getClass().getName());
			}
		};
		// when:
		converter.convert(parameter, value);
		// then: InvalidParameterValueTypeException is thrown
	}

	@Mock
	private ShaderParameterAdapter adapter;
}
