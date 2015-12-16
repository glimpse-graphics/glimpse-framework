package org.glimpseframework.api.shader;

import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;

/**
 * OpenGL shader program.
 * @author Slawomir Czerwinski
 */
public interface ShaderProgram {

	/**
	 * Links shaders into a program.
	 * @throws ShaderProgramLinkException when program linking ends with error
	 */
	void link() throws ShaderProgramLinkException;

	/**
	 * Gets a set of parameters required by the shader program.
	 * @return shader parameters
	 */
	Set<Parameter> getParameters();

	/**
	 * Chooses the program to be used for rendering.
	 */
	void use();

	/**
	 * Deletes the program and the shaders.
	 */
	void delete();

	/**
	 * Gets shader attribute parameter location.
	 * @param name attribute parameter name
	 * @return attribute parameter location
	 */
	int getAttributeLocation(String name);

	/**
	 * Gets shader uniform parameter location.
	 * @param name uniform parameter name
	 * @return uniform parameter location
	 */
	int getUniformLocation(String name);
}
