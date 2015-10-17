package org.glimpseframework.api.shader;

import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;

public interface ShaderProgram {

	void link() throws ShaderProgramLinkException;

	Set<Parameter> getParameters();

	void use();

	void delete();

	int getAttributeLocation(String name);
	int getUniformLocation(String name);
}
