package glimpse.api

/**
 * Builds a surface of revolution created by rotating a [curve] around Z axis, approximated in a given number [steps].
 */
fun revolution(steps: Int, curve: List<Vertex>) = mesh {
	val vertices = (Angle.NULL..Angle.FULL partition steps).map { angle ->
		val matrix = rotationMatrixX(angle)
		curve.map {
			Vertex(
					(matrix * it.position.toVector()).toPoint(),
					it.textureCoordinates.copy(u = it.textureCoordinates.u + Angle.FULL / angle),
					matrix * it.normal)
		}
	}
	for (step in 0..steps - 1) {
		for (vertex in 0..curve.size - 1) {
			quad(vertices[step][vertex], vertices[step + 1][vertex], vertices[step + 1][vertex + 1], vertices[step][vertex + 1])
		}
	}
}

/**
 * Builds a surface of revolution created by rotating a [curve] around Z axis, approximated in a given number of [steps].
 */
fun revolution(steps: Int, curve: () -> List<Vertex>) = revolution(steps, curve())

/**
 * Builds a sphere of a given [radius], approximated in a given number of [curveSteps] and [rotateSteps].
 */
fun sphere(curveSteps: Int, rotateSteps: Int, radius: Float = 1f) = revolution(rotateSteps) {
	(Angle.NULL..Angle.STRAIGHT partition curveSteps).map { angle ->
		val normal = rotationMatrixY(angle) * Vector.Z_UNIT
		Vertex((normal * radius).toPoint(), TextureCoordinates(0f, Angle.STRAIGHT / angle), normal)
	}
}
