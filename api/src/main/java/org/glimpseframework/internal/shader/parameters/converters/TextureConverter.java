package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.materials.Texture;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.AbstractConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;

class TextureConverter extends AbstractConverter<Texture> {

	TextureConverter(ShaderParameterAdapter adapter) {
		super(adapter);
	}

	@Override
	protected void convertUniform(Parameter parameter, Texture value) {
		adapter.uniform(parameter.getName(), value);
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.of(Parameter.Type.SAMPLER_2D);
	}
}
