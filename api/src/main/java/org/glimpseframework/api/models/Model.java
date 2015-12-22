package org.glimpseframework.api.models;

import org.glimpseframework.api.shader.ShaderProgram;

/**
 * OpenGL 3D Model.
 *
 * <p>Classes implementing this interface may define per-model values of shader parameters.</p>
 *
 * @author Slawomir Czerwinski
 */
public interface Model {

	/**
	 * Gets number of model vertices.
	 *
	 * <p>Returned value should be divisible by 3.</p>
	 *
	 * @return number of vertices
	 */
	int getNumberOfVertices();

	/**
	 * Gets shader program used to render the model.
	 * @return shader program
	 */
	ShaderProgram getShaderProgram();
}
