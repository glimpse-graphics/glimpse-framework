package org.glimpseframework.api.shader;

import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;

public interface Shader {

	enum Type {
		VERTEX_SHADER,
		FRAGMENT_SHADER;
	}

	Type getType();

	void compile() throws ShaderCompileException;

	Set<Parameter> getParameters();

	void delete();
}
