package org.glimpseframework.api.shader;

import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;

/**
 * OpenGL shader.
 * @author Slawomir Czerwinski
 */
public interface Shader {

	/**
	 * OpenGL shader type.
	 */
	enum Type {
		VERTEX_SHADER,
		FRAGMENT_SHADER;
	}

	/**
	 * Gets shader type.
	 * @return shader type
	 */
	Type getType();

	/**
	 * Compiles the shader.
	 * @throws ShaderCompileException when shader compilation ends with error
	 */
	void compile() throws ShaderCompileException;

	/**
	 * Gets a set of parameters required by the shader.
	 * @return shader parameters
	 */
	Set<Parameter> getParameters();

	/**
	 * Deletes the shader.
	 */
	void delete();
}
