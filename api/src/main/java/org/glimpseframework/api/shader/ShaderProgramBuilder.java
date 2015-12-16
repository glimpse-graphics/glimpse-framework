package org.glimpseframework.api.shader;

import java.util.ArrayList;
import java.util.List;

/**
 * OpenGL shader program builder.
 *
 * <p>Concrete implementation of this class must be provided by GlimpseFramework API implementation.</p>
 *
 * @param <S> shader implementation
 * @param <P> shader program implementation
 * @author Slawomir Czerwinski
 */
public abstract class ShaderProgramBuilder<S extends Shader, P extends ShaderProgram> {

	/**
	 * Sets sources for a shader of the requested type.
	 * @param shaderType type of shader
	 * @param source shader sources
	 * @return this builder
	 */
	public abstract ShaderProgramBuilder setSource(Shader.Type shaderType, String source);

	protected void addShader(S shader) {
		shaders.add(shader);
	}

	/**
	 * Builds a program.
	 * @return shader program
	 * @throws ShaderCompileException when shader compilation ends with error
	 * @throws ShaderProgramLinkException when program linking ends with error
	 */
	public ShaderProgram build() throws ShaderCompileException, ShaderProgramLinkException {
		shaderProgram = createProgram();
		for (Shader shader : shaders) {
			shader.compile();
		}
		shaderProgram.link();
		return shaderProgram;
	}

	protected abstract P createProgram();

	protected List<S> getShaders() {
		return shaders;
	}

	private List<S> shaders = new ArrayList<S>(Shader.Type.values().length);
	private P shaderProgram;
}
