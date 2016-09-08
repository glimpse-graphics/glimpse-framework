package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector

/**
 * A three-dimensional polygonal curve.
 *
 * @param positions Positions of the polygonal curve vertices in space.
 * @param textureCoordinates Texture coordinates at the polygonal curve vertices.
 * @param normals Normal vectors at the polygonal curve vertices.
 */
class Curve(val positions: List<Point>, val textureCoordinates: List<TextureCoordinates>, val normals: List<Vector>) {

	init {
		require(positions.size % 2 == 0) { "Positions size should divide by 2" }
		require(positions.size == textureCoordinates.size) { "Positions and textures coordinates should have the same size" }
		require(positions.size == normals.size) { "Positions and normals should have the same size" }
	}

	/**
	 * Polygonal curve vertices.
	 */
	val vertices: List<Vertex> by lazy {
		(positions zip textureCoordinates zip normals).map { Vertex(it.first.first, it.first.second, it.second) }
	}

	/**
	 * Polygonal curve segments.
	 */
	val segments: List<Pair<Vertex, Vertex>> by lazy {
		tailrec fun extractSegments(vertices: List<Vertex>, segments: List<Pair<Vertex, Vertex>>): List<Pair<Vertex, Vertex>> =
				if (vertices.isEmpty()) segments
				else extractSegments(vertices.drop(2), segments + (vertices[0] to vertices[1]))
		extractSegments(vertices, emptyList<Pair<Vertex, Vertex>>())
	}
}
