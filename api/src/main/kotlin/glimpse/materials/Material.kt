package glimpse.materials

import glimpse.cameras.Camera
import glimpse.lights.Light
import glimpse.models.Model

/**
 * Material defining a way in which a [Model] is rendered.
 */
interface Material {

	/**
	 * Renders a [Model] with a single light using this [Material].
	 */
	fun render(model: Model, camera: Camera, light: Light) =
			render(model, camera, listOf(light))

	/**
	 * Renders a [Model] with a list of lights using this [Material].
	 */
	fun render(model: Model, camera: Camera, lights: List<Light>)
}
