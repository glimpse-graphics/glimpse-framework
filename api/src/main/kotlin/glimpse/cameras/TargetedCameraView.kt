package glimpse.cameras

import glimpse.Matrix
import glimpse.Point
import glimpse.Vector
import glimpse.lookAtViewMatrix

/**
 * Targeted camera view.
 *
 * @property cameraPosition Camera position lambda.
 * @property target Camera target lambda.
 * @property up Camera up vector lambda.
 */
class TargetedCameraView(val cameraPosition: () -> Point, val target: () -> Point = { Point.ORIGIN }, val up: () -> Vector = { Vector.Z_UNIT }) : CameraView {

	override val viewMatrix: Matrix
		get() = lookAtViewMatrix(cameraPosition(), target(), up())

	override val position: Point
		get() = cameraPosition()
}