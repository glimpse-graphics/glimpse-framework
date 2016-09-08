package glimpse.models

import glimpse.Matrix
import glimpse.MatrixBuilder

/**
 * A three-dimensional model.
 *
 * @param mesh Mesh defining model's surface.
 * @param modelMatrix Model transformation matrix.
 */
class Model(val mesh: Mesh, val modelMatrix: Matrix) {

	/**
	 * Returns a [Model], transformed with the [transformationMatrix].
	 */
	fun transform(transformationMatrix: Matrix) = Model(mesh, transformationMatrix * modelMatrix)

	/**
	 * Returns a [Model], transformed with the [transformation].
	 */
	fun transform(transformation: MatrixBuilder.() -> Unit): Model {
		val builder = MatrixBuilder(modelMatrix)
		builder.transformation()
		return Model(mesh, builder.matrix)
	}
}
