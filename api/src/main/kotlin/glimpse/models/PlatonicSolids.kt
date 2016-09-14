package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector

/**
 * Returns a tetrahedron mesh of a given [size].
 */
fun tetrahedron(size: Float = 1f): Mesh = mesh {
	val vectors = arrayOf(Vector(-1f, -1f, -1f), Vector(-1f, 1f, 1f), Vector(1f, -1f, 1f), Vector(1f, 1f, -1f))
	val normals = vectors.map { it.normalize }
	val textureCoordinates = vectors.map { TextureCoordinates(Math.max(it.x, 0f), Math.max(-it.y, 0f)) }
	val positions = vectors.map { (-it * size).toPoint() }
	val positionsWithTexture = (positions zip textureCoordinates)
	(0..3).forEach { face ->
		val vertices = positionsWithTexture.filterIndexed { index, pair -> index != face }.map { Vertex(it.first, it.second, normals[face]) }
		triangle(vertices[0], vertices[1], vertices[2])
	}
}

/**
 * Returns a cube mesh of a given [size].
 */
fun cube(size: Float = 1f): Mesh = prism {
	val positions = arrayOf(Point(-size, -size), Point(size, -size), Point(size, size), Point(-size, size))
	val textureCoordinates = positions.map { TextureCoordinates(Math.max(it.x, 0f), Math.max(-it.y, 0f)) }
	val normals = arrayOf(-Vector.Y_UNIT, Vector.X_UNIT, Vector.Y_UNIT, -Vector.X_UNIT)
	curve {
		(0..3).map {
			segment(Vertex(positions[it], textureCoordinates[it], normals[it]),
					Vertex(positions[(it + 1) % 4], textureCoordinates[(it + 1) % 4], normals[it]))
		}
	}.polygon {
		listOf(Triple(1, 3, 5), Triple(5, 7, 1))
	}
}
