package glimpse.models

import glimpse.Matrix
import glimpse.MatrixBuilder

/**
 * A three-dimensional model.
 *
 * @param mesh Mesh defining model's surface.
 * @param transformation Model transformation matrix lambda.
 */
class Model(val mesh: Mesh, val transformation: () -> Matrix = { Matrix.IDENTITY }) {

	/**
	 * Returns a [Model], transformed with the [transformationMatrix].
	 */
	fun transform(transformationMatrix: Matrix) = Model(mesh) { transformationMatrix * transformation() }

	/**
	 * Returns a [Model], transformed with the [transformation].
	 */
	fun transform(transformation: MatrixBuilder.() -> Unit): Model {
		return Model(mesh) {
			val builder = MatrixBuilder(this.transformation())
			builder.transformation()
			builder.matrix
		}
	}
}
