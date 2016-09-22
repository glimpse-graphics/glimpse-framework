package glimpse.materials

import glimpse.Color
import glimpse.Matrix
import glimpse.Point
import glimpse.Vector
import glimpse.gles.*
import glimpse.gles.delegates.GLESDelegate
import glimpse.models.Mesh
import glimpse.shaders.Program
import glimpse.textures.Texture
import java.nio.FloatBuffer

/**
 * Common superclass for shader helpers.
 */
abstract class ShaderHelper : Disposable {

	/**
	 * GLES implementation.
	 */
	protected val gles: GLES by GLESDelegate

	/**
	 * Shader program.
	 */
	protected abstract val program: Program

	/**
	 * Location of a shader attribute with the given name.
	 */
	protected val String.attributeLocation: AttributeLocation
		get() = gles.getAttributeLocation(program.handle, this)

	/**
	 * Location of a shader uniform with the given name.
	 */
	protected val String.uniformLocation: UniformLocation
		get() = gles.getUniformLocation(program.handle, this)

	/**
	 * Name of a shader attribute containing vertex position.
	 */
	protected abstract val vertexPositionAttributeName: String?

	/**
	 * Name of a shader attribute containing vertex texture coordinates.
	 */
	protected abstract val vertexTextureCoordinatesAttributeName: String?

	/**
	 * Name of a shader attribute containing vertex normal.
	 */
	protected abstract val vertexNormalAttributeName: String?

	/**
	 * Tells GLES implementation to use this program.
	 */
	fun use() {
		program.use()
	}

	/**
	 * Sets [float] shader uniform value of a given [name].
	 */
	operator fun set(name: String, float: Float): Unit = gles.uniformFloat(name.uniformLocation, float)

	/**
	 * Sets [floats] shader uniform values of a given [name].
	 */
	operator fun set(name: String, floats: FloatArray): Unit = gles.uniformFloats(name.uniformLocation, floats)

	/**
	 * Sets [int] shader uniform value of a given [name].
	 */
	operator fun set(name: String, int: Int): Unit = gles.uniformInt(name.uniformLocation, int)

	/**
	 * Sets [ints] shader uniform values of a given [name].
	 */
	operator fun set(name: String, ints: IntArray): Unit = gles.uniformInts(name.uniformLocation, ints)

	/**
	 * Sets [matrix] shader uniform value of a given [name].
	 */
	operator fun set(name: String, matrix: Matrix): Unit = gles.uniformMatrix(name.uniformLocation, matrix)

	/**
	 * Sets [vector] shader uniform value of a given [name].
	 */
	operator fun set(name: String, vector: Vector): Unit = gles.uniformVector(name.uniformLocation, vector)

	/**
	 * Sets [vectors] shader uniform values of a given [name].
	 */
	fun setVectors(name: String, vectors: List<Vector>): Unit = gles.uniformVectors(name.uniformLocation, vectors)

	/**
	 * Sets [point] shader uniform value of a given [name].
	 */
	operator fun set(name: String, point: Point): Unit = gles.uniformPoint(name.uniformLocation, point)

	/**
	 * Sets [points] shader uniform values of a given [name].
	 */
	fun setPoints(name: String, points: List<Point>): Unit = gles.uniformPoints(name.uniformLocation, points)

	/**
	 * Sets [color] shader uniform value of a given [name].
	 */
	operator fun set(name: String, color: Color): Unit = gles.uniformColor(name.uniformLocation, color)

	/**
	 * Sets [colors] shader uniform values of a given [name].
	 */
	fun setColors(name: String, colors: List<Color>): Unit = gles.uniformColors(name.uniformLocation, colors)

	/**
	 * Sets [texture] shader uniform value of a given [name].
	 */
	operator fun set(name: String, index: Int, texture: Texture): Unit = texture.apply(name.uniformLocation, index)

	/**
	 * Draws a [mesh] using the shader [program].
	 */
	fun drawMesh(mesh: Mesh) {
		val positionBuffer = vertexPositionAttributeName?.attributeLocation?.set(mesh.augmentedPositionsBuffer, 4)
		val textureCoordinatesBuffer = vertexTextureCoordinatesAttributeName?.attributeLocation?.set(mesh.textureCoordinatesBuffer, 2)
		val normalBuffer = vertexNormalAttributeName?.attributeLocation?.set(mesh.augmentedNormalsBuffer, 4)

		gles.drawTriangles(mesh.count)

		positionBuffer?.dismiss()
		textureCoordinatesBuffer?.dismiss()
		normalBuffer?.dismiss()
	}

	/**
	 * Sets [buffer] of values to the attribute.
	 */
	protected fun AttributeLocation.set(buffer: FloatBuffer, vectorSize: Int): Pair<AttributeLocation, BufferHandle> {
		val bufferHandle = gles.createAttributeFloatArray(this, buffer, vectorSize)
		gles.enableAttributeArray(this)
		return this to bufferHandle
	}

	/**
	 * Dismisses the buffer of attribute values.
	 */
	protected fun Pair<AttributeLocation, BufferHandle>.dismiss() {
		gles.disableAttributeArray(first)
		gles.deleteAttributeArray(second)
	}

	/**
	 * Disposes shader helper.
	 */
	override fun dispose() {
		program.delete()
	}
}
