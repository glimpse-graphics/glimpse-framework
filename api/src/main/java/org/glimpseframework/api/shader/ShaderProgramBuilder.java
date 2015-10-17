package org.glimpseframework.api.shader;

import java.util.ArrayList;
import java.util.List;

public abstract class ShaderProgramBuilder<S extends Shader, P extends ShaderProgram> {

	public abstract ShaderProgramBuilder setSource(Shader.Type shaderType, String source);

	protected void addShader(S shader) {
		shaders.add(shader);
	}

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
