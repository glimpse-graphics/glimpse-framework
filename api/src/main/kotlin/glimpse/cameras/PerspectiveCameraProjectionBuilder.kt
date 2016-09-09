package glimpse.cameras

import glimpse.Angle
import glimpse.degrees

/**
 * Builder of a [PerspectiveCameraProjection].
 */
class PerspectiveCameraProjectionBuilder {

	private var fovY: () -> Angle = { 60.degrees }
	private var aspect: () -> Float = { 1f }
	private var near: Float = 1f
	private var far: Float = 100f

	/**
	 * Sets camera field of view angle for Y-axis (viewport height axis) lambda.
	 */
	fun fov(fovY: () -> Angle) {
		this.fovY = fovY
	}

	/**
	 * Sets camera aspect ratio lambda.
	 */
	fun aspect(aspect: () -> Float) {
		this.aspect = aspect
	}

	/**
	 * Sets camera near and far clipping planes.
	 */
	fun distanceRange(range: Pair<Float, Float>) {
		near = range.first
		far = range.second
	}

	internal fun build(): PerspectiveCameraProjection = PerspectiveCameraProjection(fovY, aspect, near, far)
}

/**
 * Builds perspective camera projecion initialized with an [init] function.
 */
fun CameraBuilder.perspective(init: PerspectiveCameraProjectionBuilder.() -> Unit) {
	val cameraProjectionBuilder = PerspectiveCameraProjectionBuilder()
	cameraProjectionBuilder.init()
	cameraProjection = cameraProjectionBuilder.build()
}
