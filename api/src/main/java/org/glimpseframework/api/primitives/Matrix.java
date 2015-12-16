package org.glimpseframework.api.primitives;

import java.util.Arrays;

/**
 * Matrix defining three-dimensional affine transformations.
 *
 * <p>A 4x4 transformation matrix.</p>
 *
 * <p>This class is immutable.</p>
 *
 * @author Slawomir Czerwinski
 */
public final class Matrix {

	/**
	 * Creates a new 4x4 matrix.
	 * <p>There must be exactly 16 elements to properly generate a matrix.</p>
	 * @param matrix matrix elements
	 * @throws InvalidMatrixSizeException when the number of elements is different than 16.
	 */
	public Matrix(float... matrix) throws InvalidMatrixSizeException {
		if (matrix.length != 16) {
			throw new InvalidMatrixSizeException(matrix.length);
		}
		this.matrix = Arrays.copyOf(matrix, 16);
	}

	/**
	 * Generates a perspective projection matrix.
	 * @param fovY field of view angle for Y-axis (viewport height axis)
	 * @param aspect width aspect ratio against height
	 * @param near near clipping plane distance
	 * @param far far clipping plane distance
	 * @return perspective projection matrix
	 */
	public static Matrix getPerspectiveMatrix(Angle fovY, float aspect, float near, float far) {
		final float top = Double.valueOf(Math.tan(fovY.getRadians() / 2.0f)).floatValue() * near;
		final float bottom = -top;
		final float left = aspect * bottom;
		final float right = aspect * top;
		return getFrustumMatrix(left, right, bottom, top, near, far);
	}

	/**
	 * Generates a frustum projection matrix.
	 * @param left left edge of near clipping plane
	 * @param right right edge of near clipping plane
	 * @param bottom bottom edge of near clipping plane
	 * @param top top edge of near clipping plane
	 * @param near near clipping plane distance
	 * @param far far clipping plane distance
	 * @return frustum projection matrix
	 */
	public static Matrix getFrustumMatrix(float left, float right, float bottom, float top, float near, float far) {
		float[] matrix = NULL_MATRIX.get16f();
		final float width = right - left;
		final float height = top - bottom;
		final float depth = near - far;
		matrix[0] = 2.0f * near / width;
		matrix[5] = 2.0f * near / height;
		matrix[8] = 2.0f * (right + left) / width;
		matrix[9] = (top + bottom) / height;
		matrix[10] = (far + near) / depth;
		matrix[11] = -1.0f;
		matrix[14] = 2.0f * far * near / depth;
		return new Matrix(matrix);
	}

	/**
	 * Generates an orthographic projection matrix.
	 * @param left left clipping plane distance
	 * @param right right clipping plane distance
	 * @param bottom bottom clipping plane distance
	 * @param top top clipping plane distance
	 * @param near near clipping plane distance
	 * @param far far clipping plane distance
	 * @return orthographic projection matrix
	 */
	public static Matrix getOrthographicMatrix(
			float left, float right, float bottom, float top, float near, float far) {
		float[] matrix = IDENTITY_MATRIX.get16f();
		final float width = right - left;
		final float height = top - bottom;
		final float depth = near - far;
		matrix[0] = 2.0f / width;
		matrix[5] = 2.0f / height;
		matrix[10] = 2.0f / depth;
		matrix[12] = -(right + left) / width;
		matrix[13] = -(top + bottom) / height;
		matrix[14] = -(near + far) / depth;
		return new Matrix(matrix);
	}

	/**
	 * Generates a look-at view matrix.
	 * @param eye position of the eye
	 * @param center position of the target center
	 * @param up up vector
	 * @return look-at view matrix
	 */
	public static Matrix getLookAtMatrix(Point eye, Point center, Vector up) {
		Vector f = new Vector(eye, center).normalize();
		Vector s = f.crossProduct(up).normalize();
		Vector u = s.crossProduct(f);
		Matrix result = new Matrix(
				s.getX(), u.getX(), -f.getX(), 0.0f,
				s.getY(), u.getY(), -f.getY(), 0.0f,
				s.getZ(), u.getZ(), -f.getZ(), 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
		return result.translate(-eye.getX(), -eye.getY(), -eye.getZ());
	}

	/**
	 * Generates rotation matrix about an axis vector.
	 * @param axis axis vector
	 * @param angle rotation angle
	 * @return rotation matrix
	 */
	public static Matrix getRotationMatrix(Vector axis, Angle angle) {
		axis = axis.normalize();
		float sin = Double.valueOf(Math.sin(angle.getRadians())).floatValue();
		float cos = Double.valueOf(Math.cos(angle.getRadians())).floatValue();
		float nCos = 1.0f - cos;
		float x = axis.getX();
		float y = axis.getY();
		float z = axis.getZ();
		return new Matrix(
				cos + x * x * nCos, x * y * nCos + z * sin, x * z * nCos - y * sin, 0.0f,
				x * y * nCos - z * sin, cos + y * y * nCos, y * z * nCos + x * sin, 0.0f,
				x * z * nCos + y * sin, y * z * nCos - x * sin, cos + z * z * nCos, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
	}

	/**
	 * Generates rotation matrix about X-axis.
	 * @param angle rotation angle
	 * @return rotation matrix
	 */
	public static Matrix getXRotationMatrix(Angle angle) {
		float sin = Double.valueOf(Math.sin(angle.getRadians())).floatValue();
		float cos = Double.valueOf(Math.cos(angle.getRadians())).floatValue();
		return new Matrix(
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, cos, sin, 0.0f,
				0.0f, -sin, cos, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
	}

	/**
	 * Generates rotation matrix about Y-axis.
	 * @param angle rotation angle
	 * @return rotation matrix
	 */
	public static Matrix getYRotationMatrix(Angle angle) {
		float sin = Double.valueOf(Math.sin(angle.getRadians())).floatValue();
		float cos = Double.valueOf(Math.cos(angle.getRadians())).floatValue();
		return new Matrix(
				cos, 0.0f, -sin, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				sin, 0.0f, cos, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
	}

	/**
	 * Generates rotation matrix about Z-axis.
	 * @param angle rotation angle
	 * @return rotation matrix
	 */
	public static Matrix getZRotationMatrix(Angle angle) {
		float sin = Double.valueOf(Math.sin(angle.getRadians())).floatValue();
		float cos = Double.valueOf(Math.cos(angle.getRadians())).floatValue();
		return new Matrix(
				cos, sin, 0.0f, 0.0f,
				-sin, cos, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
	}

	/**
	 * Creates a matrix which is a result of multiplication of two matrices.
	 * @param m second matrix
	 * @return matrix multiplication result
	 */
	public Matrix multiply(Matrix m) {
		float[] result = NULL_MATRIX.get16f();
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				for (int i = 0; i < 4; i++) {
					result[col * 4 + row] += matrix[i * 4 + row] * m.matrix[col * 4 + i];
				}
			}
		}
		return new Matrix(result);
	}

	/**
	 * Generates a transposed matrix.
	 * @return transposed matrix
	 */
	public Matrix transpose() {
		float[] matrix = get16f();
		for (int i = 0; i < 4; i++) {
			int offset = i * 4;
			matrix[i] = this.matrix[offset];
			matrix[i + 4] = this.matrix[offset + 1];
			matrix[i + 8] = this.matrix[offset + 2];
			matrix[i + 12] = this.matrix[offset + 3];
		}
		return new Matrix(matrix);
	}

	/**
	 * Generates an inverted matrix.
	 * @return inverted matrix
	 */
	public Matrix invert() {
		float[] m = transpose().get16f();
		float[] result = new float[16];
		float[] buf = new float[] {
				m[10] * m[15], m[11] * m[14], m[9] * m[15], m[11] * m[13],
				m[9] * m[14], m[10] * m[13], m[8] * m[15], m[11] * m[12],
				m[8] * m[14], m[10] * m[12], m[8] * m[13], m[9] * m[12],
		};
		result[0] = buf[0] * m[5] + buf[3] * m[6] + buf[4] * m[7];
		result[0] -= buf[1] * m[5] + buf[2] * m[6] + buf[5] * m[7];
		result[1] = buf[1] * m[4] + buf[6] * m[6] + buf[9] * m[7];
		result[1] -= buf[0] * m[4] + buf[7] * m[6] + buf[8] * m[7];
		result[2] = buf[2] * m[4] + buf[7] * m[5] + buf[10] * m[7];
		result[2] -= buf[3] * m[4] + buf[6] * m[5] + buf[11] * m[7];
		result[3] = buf[5] * m[4] + buf[8] * m[5] + buf[11] * m[6];
		result[3] -= buf[4] * m[4] + buf[9] * m[5] + buf[10] * m[6];
		result[4] = buf[1] * m[1] + buf[2] * m[2] + buf[5] * m[3];
		result[4] -= buf[0] * m[1] + buf[3] * m[2] + buf[4] * m[3];
		result[5] = buf[0] * m[0] + buf[7] * m[2] + buf[8] * m[3];
		result[5] -= buf[1] * m[0] + buf[6] * m[2] + buf[9] * m[3];
		result[6] = buf[3] * m[0] + buf[6] * m[1] + buf[11] * m[3];
		result[6] -= buf[2] * m[0] + buf[7] * m[1] + buf[10] * m[3];
		result[7] = buf[4] * m[0] + buf[9] * m[1] + buf[10] * m[2];
		result[7] -= buf[5] * m[0] + buf[8] * m[1] + buf[11] * m[2];
		buf = new float[] {
				m[2] * m[7], m[3] * m[6], m[1] * m[7], m[3] * m[5],
				m[1] * m[6], m[2] * m[5], m[0] * m[7], m[3] * m[4],
				m[0] * m[6], m[2] * m[4], m[0] * m[5], m[1] * m[4],
		};
		result[8] = buf[0] * m[13] + buf[3] * m[14] + buf[4] * m[15];
		result[8] -= buf[1] * m[13] + buf[2] * m[14] + buf[5] * m[15];
		result[9] = buf[1] * m[12] + buf[6] * m[14] + buf[9] * m[15];
		result[9] -= buf[0] * m[12] + buf[7] * m[14] + buf[8] * m[15];
		result[10] = buf[2] * m[12] + buf[7] * m[13] + buf[10] * m[15];
		result[10] -= buf[3] * m[12] + buf[6] * m[13] + buf[11] * m[15];
		result[11] = buf[5] * m[12] + buf[8] * m[13] + buf[11] * m[14];
		result[11] -= buf[4] * m[12] + buf[9] * m[13] + buf[10] * m[14];
		result[12] = buf[2] * m[10] + buf[5] * m[11] + buf[1] * m[9];
		result[12] -= buf[4] * m[11] + buf[0] * m[9] + buf[3] * m[10];
		result[13] = buf[8] * m[11] + buf[0] * m[8] + buf[7] * m[10];
		result[13] -= buf[6] * m[10] + buf[9] * m[11] + buf[1] * m[8];
		result[14] = buf[6] * m[9] + buf[11] * m[11] + buf[3] * m[8];
		result[14] -= buf[10] * m[11] + buf[2] * m[8] + buf[7] * m[9];
		result[15] = buf[10] * m[10] + buf[4] * m[8] + buf[9] * m[9];
		result[15] -= buf[8] * m[9] + buf[11] * m[10] + buf[5] * m[8];
		float det = m[0] * result[0] + m[1] * result[1] + m[2] * result[2] + m[3] * result[3];
		for (int i = 0; i < 16; i++) {
			result[i] = result[i] / det;
		}
		return new Matrix(result);
	}

	/**
	 * Generates a normal matrix.
	 * <p>A normal matrix is used by many shaders to perform calculations with normal vectors.</p>
	 * @return normal matrix
	 */
	public Matrix getNormalMatrix() {
		return invert().transpose();
	}

	/**
	 * Adds translation to existing transformation matrix.
	 * @param v translation vector
	 * @return matrix after translation
	 */
	public Matrix translate(Vector v) {
		return translate(v.getX(), v.getY(), v.getZ());
	}

	/**
	 * Adds translation to existing transformation matrix.
	 * @param x translation parallel to X-axis
	 * @param y translation parallel to Y-axis
	 * @param z translation parallel to Z-axis
	 * @return matrix after translation
	 */
	public Matrix translate(float x, float y, float z) {
		float[] result = get16f();
		for (int i = 0; i < 4; i++) {
			result[i + 12] += matrix[i] * x + matrix[i + 4] * y + matrix[i + 8] * z;
		}
		return new Matrix(result);
	}

	/**
	 * Adds rotation to existing transformation matrix.
	 * @param axis axis vector
	 * @param angle rotation angle
	 * @return matrix after rotation
	 */
	public Matrix rotate(Vector axis, Angle angle) {
		return getRotationMatrix(axis, angle).multiply(this);
	}

	/**
	 * Adds scaling to existing transformation matrix.
	 * @param scale scaling ratio
	 * @return matrix after scaling
	 */
	public Matrix scale(float scale) {
		return scale(scale, scale, scale);
	}

	/**
	 * Adds scaling to existing transformation matrix.
	 * @param x scaling along X-axis
	 * @param y scaling along Y-axis
	 * @param z scaling along Z-axis
	 * @return matrix after scaling
	 */
	public Matrix scale(float x, float y, float z) {
		float[] result = get16f();
		for (int i = 0; i < 4; i++) {
			result[i] *= x;
			result[i + 4] *= y;
			result[i + 8] *= z;
		}
		return new Matrix(result);
	}

	/**
	 * Gets affine transformation matrix coordinates, as an array of 16 float numbers.
	 * @return array of 16 float numbers
	 */
	public float[] get16f() {
		return Arrays.copyOf(matrix, 16);
	}

	@Override
	public String toString() {
		return String.format(
				"\n| %8.2f %8.2f %8.2f %8.2f |"
						+ "\n| %8.2f %8.2f %8.2f %8.2f |"
						+ "\n| %8.2f %8.2f %8.2f %8.2f |"
						+ "\n| %8.2f %8.2f %8.2f %8.2f |\n",
				matrix[0], matrix[4], matrix[8], matrix[12],
				matrix[1], matrix[5], matrix[9], matrix[13],
				matrix[2], matrix[6], matrix[10], matrix[14],
				matrix[3], matrix[7], matrix[11], matrix[15]);
	}

	/** Null matrix. */
	public static final Matrix NULL_MATRIX = new Matrix(
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f);

	/** Identity matrix. */
	public static final Matrix IDENTITY_MATRIX = new Matrix(
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f);

	private float[] matrix = new float[16];
}
