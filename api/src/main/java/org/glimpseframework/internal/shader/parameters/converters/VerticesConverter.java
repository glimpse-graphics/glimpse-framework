package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.primitives.vbo.VBO;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.AbstractConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;

class VerticesConverter extends AbstractConverter<VBO> {

	VerticesConverter(ShaderParameterAdapter adapter) {
		super(adapter);
	}

	@Override
	protected void convertAttribute(Parameter parameter, VBO value) {
		adapter.vertexAttributeBuffer(
				parameter.getName(),
				value.getVectorSize(),
				value.getType(),
				false,
				value.getType().getElementBytes() * value.getVectorSize(),
				value.getBuffer());
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.of(
				Parameter.Type.FLOAT_VECTOR_2, Parameter.Type.FLOAT_VECTOR_3, Parameter.Type.FLOAT_VECTOR_4,
				Parameter.Type.INTEGER_VECTOR_2, Parameter.Type.INTEGER_VECTOR_3, Parameter.Type.INTEGER_VECTOR_4);
	}
}
