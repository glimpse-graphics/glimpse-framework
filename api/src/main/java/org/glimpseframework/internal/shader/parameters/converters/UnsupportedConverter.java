package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedAttributeException;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedUniformException;

class UnsupportedConverter implements ParameterConverter<Object> {

	@Override
	public void convert(Parameter parameter, Object value) {
		switch (parameter.getScope()) {
			case ATTRIBUTE:
				throw new UnsupportedAttributeException(value);
			case UNIFORM:
				throw new UnsupportedUniformException(value);
			default:
		}
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.noneOf(Parameter.Type.class);
	}
}
