package glimpse.api

/**
 * Transformation matrix for a rotation by an [angle] around an [axis].
 */
fun rotationMatrix(axis: Vector, angle: Angle): Matrix {
	val (x, y, z) = axis.normalize
	val sin = sin(angle)
	val cos = cos(angle)
	val nCos = 1f - cos(angle)
	return Matrix(listOf(
			cos + x * x * nCos, x * y * nCos + z * sin, x * z * nCos - y * sin, 0f,
			x * y * nCos - z * sin, cos + y * y * nCos, y * z * nCos + x * sin, 0f,
			x * z * nCos + y * sin, y * z * nCos - x * sin, cos + z * z * nCos, 0f,
			0f, 0f, 0f, 1f))
}

/**
 * Transformation matrix for a rotation by an [angle] around X axis.
 */
fun rotationMatrixX(angle: Angle): Matrix {
	val sin = sin(angle)
	val cos = cos(angle)
	return Matrix(listOf(
			1f, 0f, 0f, 0f,
			0f, cos, sin, 0f,
			0f, -sin, cos, 0f,
			0f, 0f, 0f, 1f))
}

/**
 * Transformation matrix for a rotation by an [angle] around Y axis.
 */
fun rotationMatrixY(angle: Angle): Matrix {
	val sin = sin(angle)
	val cos = cos(angle)
	return Matrix(listOf(
			cos, 0f, -sin, 0f,
			0f, 1f, 0f, 0f,
			sin, 0f, cos, 0f,
			0f, 0f, 0f, 1f))
}

/**
 * Transformation matrix for a rotation by an [angle] around Z axis.
 */
fun rotationMatrixZ(angle: Angle): Matrix {
	val sin = sin(angle)
	val cos = cos(angle)
	return Matrix(listOf(
			cos, sin, 0f, 0f,
			-sin, cos, 0f, 0f,
			0f, 0f, 1f, 0f,
			0f, 0f, 0f, 1f))
}
