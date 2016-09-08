package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector

/**
 * A three-dimensional polygonal curve builder.
 */
class CurveBuilder {

	private val positions = mutableListOf<Point>()
	private val textureCoordinates = mutableListOf<TextureCoordinates>()
	private val normals = mutableListOf<Vector>()

	internal fun toCurve() = Curve(positions.toList(), textureCoordinates.toList(), normals.toList())

	private fun push(vertex: Vertex) {
		positions.add(vertex.position)
		textureCoordinates.add(vertex.textureCoordinates)
		normals.add(vertex.normal)
	}

	private fun push(vararg vertices: Vertex) {
		vertices.forEach { push(it) }
	}

	/**
	 * Adds a segment to the [Curve].
	 */
	fun segment(v1: Vertex, v2: Vertex) {
		push(v1, v2)
	}

	/**
	 * Adds a segment to the [Curve].
	 */
	fun segment(segment: Pair<Vertex, Vertex>) {
		segment(segment.first, segment.second)
	}
}

/**
 * Builds a polygonal curve initialized with an [init] function.
 */
fun curve(init: CurveBuilder.() -> Unit): Curve {
	val builder = CurveBuilder()
	builder.init()
	return builder.toCurve()
}
