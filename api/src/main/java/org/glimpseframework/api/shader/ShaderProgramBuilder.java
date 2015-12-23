package org.glimpseframework.api.shader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.internal.shader.parameters.parser.ParameterParser;

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

	/**
	 * Prepares a shader to be added to the shader program.
	 * @param shader shader
	 */
	protected final void addShader(S shader) {
		shaders.add(shader);
	}

	/**
	 * Parses shader parameters from shader source code.
	 * @param source shader source code
	 * @return shader parameters
	 */
	protected final Set<Parameter> parseParameters(String source) {
		return parser.parse(source);
	}

	/**
	 * Builds a program.
	 * @return shader program
	 * @throws ShaderCompileException when shader compilation ends with error
	 * @throws ShaderProgramLinkException when program linking ends with error
	 */
	public final ShaderProgram build() throws ShaderCompileException, ShaderProgramLinkException {
		shaderProgram = createProgram();
		for (Shader shader : shaders) {
			shader.compile();
		}
		shaderProgram.link();
		return shaderProgram;
	}

	/**
	 * Creates a new shader program.
	 * @return new shader program
	 */
	protected abstract P createProgram();

	/**
	 * Gets shaders to be added to the shader program.
	 * @return shaders
	 */
	protected final List<S> getShaders() {
		return shaders;
	}

	private ParameterParser parser = new ParameterParser();

	private List<S> shaders = new ArrayList<S>(Shader.Type.values().length);
	private P shaderProgram;
}
