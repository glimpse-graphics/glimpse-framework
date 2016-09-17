package glimpse.cameras

import glimpse.Angle
import glimpse.Point

/**
 * Builder of a [FreeformCameraView].
 */
class FreeformCameraViewBuilder() {

	private var position: () -> Point = { Point.ORIGIN }
	private var roll: () -> Angle = { Angle.NULL }
	private var pitch: () -> Angle = { Angle.NULL }
	private var yaw: () -> Angle = { Angle.NULL }

	/**
	 * Sets camera position lambda.
	 */
	fun position(position: () -> Point) {
		this.position = position
	}

	/**
	 * Sets camera roll angle lambda.
	 */
	fun roll(roll: () -> Angle) {
		this.roll = roll
	}

	/**
	 * Sets camera pitch angle lambda.
	 */
	fun pitch(pitch: () -> Angle) {
		this.pitch = pitch
	}

	/**
	 * Sets camera yaw angle lambda.
	 */
	fun yaw(yaw: () -> Angle) {
		this.yaw = yaw
	}

	internal fun build(): FreeformCameraView = FreeformCameraView(position, roll, pitch, yaw)
}

/**
 * Builds free-form camera view initialized with an [init] function.
 */
fun CameraBuilder.freeform(init: FreeformCameraViewBuilder.() -> Unit) {
	val cameraViewBuilder = FreeformCameraViewBuilder()
	cameraViewBuilder.init()
	cameraView = cameraViewBuilder.build()
}
