package glimpse.cameras

import kotlin.properties.Delegates

/**
 * Camera builder.
 */
class CameraBuilder {

	internal var cameraView: CameraView by Delegates.notNull()
	internal var cameraProjection: CameraProjection by Delegates.notNull()

	internal fun build(): Camera = Camera(cameraView, cameraProjection)
}

/**
 * Builds a [Camera] initialized with an [init] function.
 */
fun camera(init: CameraBuilder.() -> Unit): Camera {
	val builder = CameraBuilder()
	builder.init()
	return builder.build()
}
