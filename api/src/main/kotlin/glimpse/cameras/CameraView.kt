package glimpse.cameras

import glimpse.Matrix
import glimpse.Point

/**
 * Camera view.
 */
interface CameraView {

	/**
	 * View matrix.
	 */
	val viewMatrix: Matrix

	/**
	 * Camera position.
	 */
	val position: Point
}