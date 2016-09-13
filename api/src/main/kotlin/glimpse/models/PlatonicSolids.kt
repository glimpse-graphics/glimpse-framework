package glimpse.models

import glimpse.TextureCoordinates
import glimpse.Vector

fun tetrahedron(size: Float = 1f) = mesh {
	val vectors = arrayOf(Vector(-1f, -1f, -1f), Vector(-1f, 1f, 1f), Vector(1f, -1f, 1f), Vector(1f, 1f, -1f))
	val normals = vectors.map { it.normalize }
	val textureCoordinates = vectors.map { TextureCoordinates(Math.max(it.x, 0f), Math.max(it.y, 0f)) }
	val positions = vectors.map { (-it * size * 0.5f).toPoint() }
	val positionsWithTexture = (positions zip textureCoordinates)
	(0..3).forEach { face ->
		val vertices = positionsWithTexture.filterIndexed { index, pair -> index != face }.map { Vertex(it.first, it.second, normals[face]) }
		triangle(vertices[0], vertices[1], vertices[2])
	}
}
