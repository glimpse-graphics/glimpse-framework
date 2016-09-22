package glimpse.cameras

import glimpse.Angle
import glimpse.Matrix
import glimpse.orthographicProjectionMatrix
import glimpse.perspectiveProjectionMatrix

/**
 * Orthographic camera projection.
 *
 * @property height Projected region height lambda.
 * @property aspect Width aspect ratio against height lambda.
 * @property near Near clipping plane distance.
 * @property far Far clipping plane distance.
 */
class OrthographicCameraProjection(val height: () -> Float, val aspect: () -> Float, val near: Float, val far: Float) : CameraProjection {

	override val projectionMatrix: Matrix
		get() {
			val top = height() * .5f
			val right = top * aspect()
			return orthographicProjectionMatrix(-right, right, -top, top, near, far)
		}
}