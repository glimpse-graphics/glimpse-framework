package glimpse

/**
 * Returns a frustum projection [Matrix].
 * The [left] to [right], [top] to [bottom] rectangle is the [near] clipping plane of the frustum.
 */
fun frustumProjectionMatrix(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix {
	val width = right - left
	val height = top - bottom
	val depth = near - far
	return Matrix(listOf(
			2f * near / width, 0f, 0f, 0f,
			0f, 2f * near / height, 0f, 0f,
			(right + left) / width, (top + bottom) / height, (far + near) / depth, -1f,
			0f, 0f, 2f * far * near / depth, 0f))
}

/**
 * Returns a perspective projection [Matrix].
 *
 * @param fovY Field of view angle for Y-axis (viewport height axis).
 * @param aspect Width aspect ratio against height.
 * @param near Near clipping plane distance.
 * @param far Far clipping plane distance.
 */
fun perspectiveProjectionMatrix(fovY: Angle, aspect: Float, near: Float, far: Float): Matrix {
	val top = tan(fovY / 2f) * near
	val right = aspect * top
	val depth = near - far
	return Matrix(listOf(
			1f / right, 0f, 0f, 0f,
			0f, 1f / top, 0f, 0f,
			0f, 0f, (near + far) / depth, -1f,
			0f, 0f, 2 * near * far / depth, 0f))
}

/**
 * Returns an orthographic projection [Matrix].
 */
fun orthographicProjectionMatrix(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix {
	val width = right - left
	val height = top - bottom
	val depth = far - near
	return Matrix(listOf(
			2f / width, 0f, 0f, 0f,
			0f, 2f / height, 0f, 0f,
			0f, 0f, -2f / depth, 0f,
			-(right + left) / width, -(top + bottom) / height, -(near + far) / depth, 1f))
}


/**
 * Returns a look-at view [Matrix].
 */
fun lookAtViewMatrix(eye: Point, center: Point, up: Vector): Matrix {
	val f = (eye to center).normalize
	val s = (f * up).normalize
	val u = s * f
	return Matrix(listOf(
			s.x, u.x, -f.x, 0f,
			s.y, u.y, -f.y, 0f,
			s.z, u.z, -f.z, 0f,
			0f, 0f, 0f, 1f)) * translationMatrix(-eye.toVector())
}


/**
 * Returns a transformation [Matrix] for a translation by an [vector].
 */
fun translationMatrix(vector: Vector): Matrix {
	val (x, y, z) = vector
	return Matrix(listOf(
			1f, 0f, 0f, 0f,
			0f, 1f, 0f, 0f,
			0f, 0f, 1f, 0f,
			x, y, z, 1f))
}

/**
 * Returns a transformation [Matrix] for a rotation by an [angle] around an [axis].
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
 * Returns a transformation [Matrix] for a rotation by an [angle] around X axis.
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
 * Returns a transformation [Matrix] for a rotation by an [angle] around Y axis.
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
 * Returns a transformation [Matrix] for a rotation by an [angle] around Z axis.
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

/**
 * Returns a transformation [Matrix] for uniform scaling.
 */
fun scalingMatrix(scale: Float): Matrix = scalingMatrix(scale, scale, scale)

/**
 * Returns a transformation [Matrix] for scaling.
 */
fun scalingMatrix(x: Float = 1f, y: Float = 1f, z: Float = 1f): Matrix =
		Matrix(listOf(
				x, 0f, 0f, 0f,
				0f, y, 0f, 0f,
				0f, 0f, z, 0f,
				0f, 0f, 0f, 1f))

/**
 * Returns a transformation [Matrix] for reflection through a plane,
 * defined by a [normal] vector and a [point] on the plane.
 */
fun reflectionMatrix(normal: Vector, point: Point): Matrix {
	val (a, b, c) = normal.normalize
	val d = -point.toVector() dot (normal.normalize)
	return Matrix(listOf(
			1f - 2f * a * a, -2f * b * a, -2f * c * a, 0f,
			-2f * a * b, 1f - 2f * b * b, -2f * c * b, 0f,
			-2f * a * c, -2f * b * c, 1f - 2f * c * c, 0f,
			-2f * a * d, -2f * b * d, -2f * c * d, 1f))
}
