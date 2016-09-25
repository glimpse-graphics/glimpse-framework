package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector

/**
 * A three-dimensional mesh builder.
 */
class MeshBuilder {

	private val positions = mutableListOf<Point>()
	private val textureCoordinates = mutableListOf<TextureCoordinates>()
	private val normals = mutableListOf<Vector>()

	internal fun build() = Mesh(positions.toList(), textureCoordinates.toList(), normals.toList())

	internal fun push(vertex: Vertex) {
		positions.add(vertex.position)
		textureCoordinates.add(vertex.textureCoordinates)
		normals.add(vertex.normal)
	}

	private fun push(vararg vertices: Vertex) {
		vertices.forEach { push(it) }
	}

	/**
	 * Adds a triangular face to the [Mesh].
	 */
	fun triangle(v1: Vertex, v2: Vertex, v3: Vertex) {
		push(v1, v2, v3)
	}

	/**
	 * Adds a quadrilateral face to the [Mesh].
	 */
	fun quad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex) {
		triangle(v1, v2, v3)
		triangle(v3, v4, v1)
	}
}

/**
 * Builds a mesh initialized with an [init] function.
 */
fun mesh(init: MeshBuilder.() -> Unit): Mesh {
	val builder = MeshBuilder()
	builder.init()
	return builder.build()
}