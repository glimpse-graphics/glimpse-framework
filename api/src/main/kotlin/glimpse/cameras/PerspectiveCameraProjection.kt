package glimpse.cameras

import glimpse.Angle
import glimpse.Matrix
import glimpse.perspectiveProjectionMatrix

/**
 * Perspective camera projection.
 *
 * @property fovY Field of view angle for Y-axis (viewport height axis) lambda.
 * @property aspect Width aspect ratio against height lambda.
 * @property near Near clipping plane distance.
 * @property far Far clipping plane distance.
 */
class PerspectiveCameraProjection(val fovY: () -> Angle, val aspect: () -> Float, val near: Float, val far: Float) : CameraProjection {

	override val projectionMatrix: Matrix
		get() = perspectiveProjectionMatrix(fovY(), aspect(), near, far)
}