package glimpse

/**
 * Affine transformation matrix builder.
 *
 * @param matrix Initial matrix.
 */
class MatrixBuilder(internal var matrix: Matrix = Matrix.IDENTITY) {

	private fun transform(transformation: Matrix) {
		matrix = transformation * matrix
	}

	/**
	 * Translates by a vector.
	 */
	fun translate(vector: Vector) {
		transform(translationMatrix(vector))
	}

	/**
	 * Rotates by an [angle] around an [axis].
	 */
	fun rotate(axis: Vector, angle: Angle) {
		transform(rotationMatrix(axis, angle))
	}

	/**
	 * Rotates by an [angle] around X axis.
	 */
	fun rotateX(angle: Angle) {
		transform(rotationMatrixX(angle))
	}

	/**
	 * Rotates by an [angle] around Y axis.
	 */
	fun rotateY(angle: Angle) {
		transform(rotationMatrixY(angle))
	}

	/**
	 * Rotates by an [angle] around Z axis.
	 */
	fun rotateZ(angle: Angle) {
		transform(rotationMatrixZ(angle))
	}

	/**
	 * Scales by a [scale] factor.
	 */
	fun scale(scale: Float) {
		transform(scalingMatrix(scale))
	}

	/**
	 * Scales by a [x], [y], [z] factors along X, Y and Z axes.
	 */
	fun scale(x: Float = 1f, y: Float = 1f, z: Float = 1f) {
		transform(scalingMatrix(x, y, z))
	}

	/**
	 * Reflects through a plane, defined by a [normal] vector and a [point] on the plane.
	 */
	fun reflect(normal: Vector, point: Point) {
		transform(reflectionMatrix(normal, point))
	}
}

/**
 * Builds an affine [transformation] matrix function.
 */
fun matrix(transformation: MatrixBuilder.() -> Unit): () -> Matrix = {
	val builder = MatrixBuilder()
	builder.transformation()
	builder.matrix
}

/**
 * Builds an affine [transformation] matrix function, starting from given [transformationMatrix].
 */
fun matrix(transformationMatrix: Matrix, transformation: MatrixBuilder.() -> Unit): () -> Matrix = {
	val builder = MatrixBuilder(transformationMatrix)
	builder.transformation()
	builder.matrix
}

/**
 * Builds an affine [transformation] matrix function, starting from given [transformationMatrix] lambda.
 */
fun matrix(transformationMatrix: () -> Matrix, transformation: MatrixBuilder.() -> Unit): () -> Matrix = {
	val builder = MatrixBuilder(transformationMatrix())
	builder.transformation()
	builder.matrix
}
