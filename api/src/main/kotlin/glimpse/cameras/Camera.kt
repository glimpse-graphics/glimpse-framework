package glimpse.cameras

import glimpse.Matrix
import glimpse.Point

/**
 * Camera.
 *
 * @property view Camera view.
 * @property projection Camera projection.
 */
class Camera(val view: CameraView, val projection: CameraProjection) {

	/**
	 * Camera transformation matrix.
	 */
	val cameraMatrix: Matrix
		get() = projection.projectionMatrix * view.viewMatrix

	/**
	 * Camera position.
	 */
	val position: Point
		get() = view.position
}
