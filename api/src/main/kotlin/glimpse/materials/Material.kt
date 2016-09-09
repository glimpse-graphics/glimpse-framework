package glimpse.materials

import glimpse.Matrix
import glimpse.models.Model

/**
 * Material defining a way in which a [Model] is rendered.
 */
interface Material {

	/**
	 * Renders a [Model] using this [Material].
	 */
	fun render(model: Model, vpMatrix: Matrix)

	/**
	 * Disposes material.
	 */
	fun dispose()
}