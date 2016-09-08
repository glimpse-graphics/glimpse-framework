package glimpse.models

import glimpse.*

/**
 * Builds a surface of revolution created by rotating a [curve] around Z axis, approximated in a given number [steps].
 */
fun revolution(steps: Int, curve: Curve) = mesh {
	fun Vertex.rotate(matrix: Matrix, angle: Angle): Vertex = Vertex(
			(matrix * position.toVector()).toPoint(),
			textureCoordinates.copy(u = textureCoordinates.u + Angle.FULL / angle),
			matrix * normal)

	val segments = (Angle.NULL..Angle.FULL partition steps).map { angle ->
		val matrix = rotationMatrixZ(angle)
		curve.segments.map {
			it.first.rotate(matrix, angle) to it.second.rotate(matrix, angle)
		}
	}
	for (step in 0..steps - 1) {
		for (segment in 0..curve.segments.size - 1) {
			val (v1, v2) = segments[step][segment]
			val (v4, v3) = segments[step + 1][segment]
			quad(v1, v2, v3, v4)
		}
	}
}

/**
 * Builds a surface of revolution created by rotating a [curve] around Z axis, approximated in a given number of [steps].
 */
fun revolution(steps: Int, curve: () -> Curve) = revolution(steps, curve())

/**
 * Builds a sphere of a given [radius], approximated in a given number of [curveSteps] and [rotateSteps].
 */
fun sphere(curveSteps: Int, rotateSteps: Int = curveSteps * 2, radius: Float = 1f) = revolution(rotateSteps) {
	curve {
		val vertices = (Angle.NULL..Angle.STRAIGHT partition curveSteps).map { angle ->
			val normal = rotationMatrixY(angle) * Vector.Z_UNIT
			Vertex((normal * radius).toPoint(), TextureCoordinates(0f, Angle.STRAIGHT / angle), normal)
		}
		vertices.dropLast(1).zip(vertices.drop(1)).forEach {
			segment(it)
		}
	}
}
