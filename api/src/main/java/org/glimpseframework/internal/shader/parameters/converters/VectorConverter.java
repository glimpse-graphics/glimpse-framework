package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.primitives.Vector;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.AbstractConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;

class VectorConverter extends AbstractConverter<Vector> {

	VectorConverter(ShaderParameterAdapter adapter) {
		super(adapter);
	}

	@Override
	protected void convertUniform(Parameter parameter, Vector value) {
		switch (parameter.getType()) {
			case FLOAT_VECTOR_4:
				adapter.uniform(parameter.getName(), value.get4f());
				break;
			case FLOAT_VECTOR_3:
			default:
				adapter.uniform(parameter.getName(), value.get3f());
				break;
		}
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.of(Parameter.Type.FLOAT_VECTOR_3, Parameter.Type.FLOAT_VECTOR_4);
	}
}
