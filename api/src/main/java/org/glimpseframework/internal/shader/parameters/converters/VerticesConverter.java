package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.primitives.vbo.VBO;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedUniformException;

class VerticesConverter implements ParameterConverter<VBO> {

	private ShaderParameterAdapter adapter;

	VerticesConverter(ShaderParameterAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void convert(Parameter parameter, VBO value) {
		switch (parameter.getScope()) {
			case UNIFORM:
				throw new UnsupportedUniformException(VBO.class);
			case ATTRIBUTE:
				adapter.vertexAttributeBuffer(
						parameter.getName(),
						value.getVectorSize(),
						value.getType(),
						false,
						value.getType().getElementBytes() * value.getVectorSize(),
						value.getBuffer());
			default:
		}
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.of(
				Parameter.Type.FLOAT_VECTOR_2, Parameter.Type.FLOAT_VECTOR_3, Parameter.Type.FLOAT_VECTOR_4,
				Parameter.Type.INTEGER_VECTOR_2, Parameter.Type.INTEGER_VECTOR_3, Parameter.Type.INTEGER_VECTOR_4);
	}
}
