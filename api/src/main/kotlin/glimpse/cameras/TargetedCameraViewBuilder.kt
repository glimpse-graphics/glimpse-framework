package glimpse.cameras

import glimpse.Point
import glimpse.Vector

/**
 * Builder of a [TargetedCameraView].
 */
class TargetedCameraViewBuilder() {

	private var position: () -> Point = { Vector.X_UNIT.toPoint() }
	private var target: () -> Point = { Point.ORIGIN }
	private var up: () -> Vector = { Vector.Z_UNIT }

	/**
	 * Sets camera position lambda.
	 */
	fun position(position: () -> Point) {
		this.position = position
	}

	/**
	 * Sets camera target lambda.
	 */
	fun target(target: () -> Point) {
		this.target = target
	}

	/**
	 * Sets camera up vector lambda.
	 */
	fun up(up: () -> Vector) {
		this.up = up
	}

	internal fun build(): TargetedCameraView = TargetedCameraView(position, target, up)
}

/**
 * Builds targeted camera view initialized with an [init] function.
 */
fun CameraBuilder.targeted(init: TargetedCameraViewBuilder.() -> Unit) {
	val cameraViewBuilder = TargetedCameraViewBuilder()
	cameraViewBuilder.init()
	cameraView = cameraViewBuilder.build()
}
