package glimpse.models

import glimpse.*

/**
 * A three-dimensional mesh.
 *
 * @param positions Positions of the mesh vertices in space.
 * @param textureCoordinates Texture coordinates at the mesh vertices.
 * @param normals Normal vectors at the mesh vertices.
 */
class Mesh(val positions: List<Point>, val textureCoordinates: List<TextureCoordinates>, val normals: List<Vector>) {

	init {
		require(positions.size % 3 == 0) { "Positions size should divide by 3" }
		require(positions.size == textureCoordinates.size) { "Positions and textures coordinates should have the same size" }
		require(positions.size == normals.size) { "Positions and normals should have the same size" }
	}

	/**
	 * Mesh vertices counter.
	 */
	val count = positions.size

	/**
	 * Mesh vertices.
	 */
	val vertices: List<Vertex> by lazy {
		(positions zip textureCoordinates zip normals).map { Vertex(it.first.first, it.first.second, it.second) }
	}

	/**
	 * Mesh faces.
	 */
	val faces: List<Face> by lazy {
		tailrec fun extractFaces(vertices: List<Vertex>, faces: List<Face>): List<Face> =
			if (vertices.isEmpty()) faces
			else extractFaces(vertices.drop(3), faces + Face(vertices[0], vertices[1], vertices[2]))
		extractFaces(vertices, emptyList<Face>())
	}

	/**
	 * Returns a [Model] from the [Mesh], transformed with the [transformationMatrix].
	 */
	fun transform(transformationMatrix: Matrix) =
			transform(transformationMatrix) {}

	/**
	 * Returns a [Model] from the [Mesh], transformed with the [transformation] applied to the initial [transformationMatrix].
	 */
	fun transform(transformationMatrix: Matrix, transformation: MatrixBuilder.() -> Unit) =
			Model(this, matrix(transformationMatrix, transformation))

	/**
	 * Returns a [Model] from the [Mesh], transformed with the [transformation].
	 */
	fun transform(transformation: MatrixBuilder.() -> Unit) =
			Model(this, matrix(transformation))
}
