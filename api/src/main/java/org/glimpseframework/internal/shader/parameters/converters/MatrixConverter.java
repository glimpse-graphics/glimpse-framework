package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.primitives.Matrix;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.ParameterConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.api.shader.parameters.converters.UnsupportedAttributeException;

class MatrixConverter implements ParameterConverter<Matrix> {

	private ShaderParameterAdapter adapter;

	MatrixConverter(ShaderParameterAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void convert(Parameter parameter, Matrix value) {
		switch (parameter.getScope()) {
			case ATTRIBUTE:
				throw new UnsupportedAttributeException(Matrix.class);
			case UNIFORM:
				adapter.uniformMatrix4(parameter.getName(), 1, false, value.get16f(), 0);
			default:
		}
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.of(Parameter.Type.MATRIX_4);
	}
}
