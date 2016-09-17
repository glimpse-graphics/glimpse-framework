package glimpse.cameras

import glimpse.Angle
import glimpse.Matrix
import glimpse.Point
import glimpse.matrix

/**
 * Free-form camera view.
 *
 * @property cameraPosition Camera position lambda.
 * @property roll Camera roll lambda.
 * @property pitch Camera pitch lambda.
 * @property yaw Camera yaw lambda.
 */
class FreeformCameraView(val cameraPosition: () -> Point, val roll: () -> Angle, val pitch: () -> Angle, val yaw: () -> Angle) : CameraView {

	companion object {

		private val DEFAULT_MATRIX = Matrix(listOf(
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, -1.0f, 0.0f,
				-1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f))
	}

	private val viewMatrixFunction = matrix(DEFAULT_MATRIX) {
		translate(-position.toVector())
		rotateZ(-yaw())
		rotateY(-pitch())
		rotateX(-roll())
	}

	override val viewMatrix: Matrix
		get() = viewMatrixFunction()

	override val position: Point
		get() = cameraPosition()
}