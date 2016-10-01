package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector
import glimpse.io.Resource
import java.io.File
import java.io.InputStream

internal class ObjMeshBuilder(lines: List<String>) {

	private val words = lines.map { it.trim().split(" ", "\t") }

	private val positions: List<Point> by lazy {
		words.filter { it.isPositionLine }.map { it.parsePoint() }
	}

	private val textureCoordinates: List<TextureCoordinates> by lazy {
		words.filter { it.isTextureCoordinatesLine }.map { it.parseTextureCoordinates() }
	}

	private val normals: List<Vector> by lazy {
		words.filter { it.isNormalLine }.map { it.parseVector() }
	}

	private val List<String>.isCommentLine: Boolean
		get() = first() == "#"

	private val List<String>.isNextMeshLine: Boolean
		get() = first() == "o"

	private val List<String>.isPositionLine: Boolean
		get() = first() == "v"

	private val List<String>.isTextureCoordinatesLine: Boolean
		get() = first() == "vt"

	private val List<String>.isNormalLine: Boolean
		get() = first() == "vn"

	private val List<String>.isFaceLine: Boolean
		get() = first() == "f"

	private fun List<String>.parsePoint(): Point =
			Point(this[1].toFloat(), this[2].toFloat(), this[3].toFloat())

	private fun List<String>.parseTextureCoordinates(): TextureCoordinates =
			TextureCoordinates(this[1].toFloat(), this[2].toFloat())

	private fun List<String>.parseVector(): Vector =
			Vector(this[1].toFloat(), this[2].toFloat(), this[3].toFloat())

	private fun String.parseVertex(): Vertex {
		val indices = this.split("/").map {
			if(it.isBlank()) -1
			else it.toInt() - 1
		}
		return Vertex(positions(indices[0]), textureCoordinates(indices[1]), normals(indices[2]))
	}

	private fun positions(index: Int): Point =
			if (index < 0) Point.ORIGIN
			else positions[index]

	private fun textureCoordinates(index: Int): TextureCoordinates =
			if (index < 0) TextureCoordinates.BOTTOM_LEFT
			else textureCoordinates[index]

	private fun normals(index: Int): Vector =
			if (index < 0) Vector.NULL
			else normals[index]

	internal fun build(): List<Mesh> {
		val list = mutableListOf<Mesh>()
		var builder = MeshBuilder()
		words.filter { it.isNextMeshLine || it.isFaceLine }.dropWhile { it.isNextMeshLine }.forEach {
			when {
				it.isNextMeshLine -> {
					list.add(builder.build())
					builder = MeshBuilder()
				}
				it.isFaceLine -> it.drop(1).map { it.parseVertex() }.forEach { builder.push(it) }
			}
		}
		list.add(builder.build())
		return list
	}
}

/**
 * Builds three-dimensional meshes from OBJ lines.
 */
fun List<String>.loadObjMesh(): List<Mesh> = ObjMeshBuilder(this).build()

/**
 * Builds three-dimensional meshes from OBJ [InputStream].
 */
fun InputStream.loadObjMesh(): List<Mesh> = reader().readLines().loadObjMesh()

/**
 * Builds three-dimensional meshes from OBJ [File].
 */
fun File.loadObjMesh(): List<Mesh> = readLines().loadObjMesh()

/**
 * Builds three-dimensional meshes from OBJ file [Resource].
 */
fun Resource.loadObjMesh(): List<Mesh> = lines.loadObjMesh()
