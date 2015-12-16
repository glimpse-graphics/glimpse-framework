package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.primitives.Angle;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.AbstractConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;

class AngleConverter extends AbstractConverter<Angle> {

	AngleConverter(ShaderParameterAdapter adapter) {
		super(adapter);
	}

	@Override
	protected void convertUniform(Parameter parameter, Angle value) {
		adapter.uniform(parameter.getName(), value.getRadians());
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.of(Parameter.Type.FLOAT);
	}
}
