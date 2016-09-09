package glimpse.cameras

import glimpse.Matrix

/**
 * Camera projection.
 */
interface CameraProjection {

	/**
	 * Projection matrix.
	 */
	val projectionMatrix: Matrix
}