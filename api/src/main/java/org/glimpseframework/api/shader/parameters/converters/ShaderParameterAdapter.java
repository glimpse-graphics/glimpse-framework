package org.glimpseframework.api.shader.parameters.converters;

import java.nio.Buffer;
import org.glimpseframework.api.primitives.vbo.VBO;
import org.glimpseframework.api.shader.ShaderProgram;

/**
 * OpenGL shader parameters adapter.
 *
 * <p>Concrete implementation of this class must be provided by GlimpseFramework API implementation.</p>
 *
 * @author Slawomir Czerwinski
 */
public abstract class ShaderParameterAdapter {

	/**
	 * Creates a new adapter.
	 * @param shaderProgram shader program
	 */
	public ShaderParameterAdapter(ShaderProgram shaderProgram) {
		this.shaderProgram = shaderProgram;
	}

	/**
	 * Applies a buffer containing attribute parameters of vertices.
	 * @param parameterName shader parameter name
	 * @param vectorSize single vector size
	 * @param type data type
	 * @param normalized {@code true} if the values should be normalized,
	 *                   or {@code false} if they should be applied directly
	 * @param stride byte offset between subsequent vectors
	 * @param buffer buffer containing actual values
	 */
	public abstract void vertexAttributeBuffer(
			String parameterName, int vectorSize, VBO.VBOType type, boolean normalized, int stride, Buffer buffer);

	/**
	 * Applies 4x4 uniform matrix.
	 * @param parameterName shader parameter name
	 * @param count amount of matrices
	 * @param transpose {@code true} if the matrix should be transposed, {@code false} otherwise
	 * @param value matrix elements
	 * @param offset offset in the array of matrix elements
	 */
	public abstract void uniformMatrix4(String parameterName, int count, boolean transpose, float[] value, int offset);

	/**
	 * Applies uniform integer values.
	 * @param parameterName shader parameter name
	 * @param values 1 to 4 values
	 */
	public abstract void uniform(String parameterName, int... values);

	/**
	 * Applies uniform floating point values.
	 * @param parameterName shader parameter name
	 * @param values 1 to 4 values
	 */
	public abstract void uniform(String parameterName, float... values);

	protected ShaderProgram shaderProgram;
}
