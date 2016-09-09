package glimpse.materials

import glimpse.*
import glimpse.gles.AttributeLocation
import glimpse.gles.BufferHandle
import glimpse.gles.GLES
import glimpse.gles.UniformLocation
import glimpse.models.Mesh
import glimpse.shaders.Program
import java.nio.FloatBuffer

/**
 * Common superclass for shader helpers.
 */
abstract class ShaderHelper {

	/**
	 * GLES implementation.
	 */
	protected var gles: GLES by GLESDelegate()

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
	 * Initializes shader helper with [GLES] implementation.
	 */
	fun init(gles: GLES) {
		this.gles = gles
	}

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
	 * Sets [int] shader uniform value of a given [name].
	 */
	operator fun set(name: String, int: Int): Unit = gles.uniformInt(name.uniformLocation, int)

	/**
	 * Sets [matrix] shader uniform value of a given [name].
	 */
	operator fun set(name: String, matrix: Matrix): Unit = gles.uniformMatrix(name.uniformLocation, matrix)

	/**
	 * Sets [vector] shader uniform value of a given [name].
	 */
	operator fun set(name: String, vector: Vector): Unit = gles.uniformVector(name.uniformLocation, vector)

	/**
	 * Sets [point] shader uniform value of a given [name].
	 */
	operator fun set(name: String, point: Point): Unit = gles.uniformPoint(name.uniformLocation, point)

	/**
	 * Sets [color] shader uniform value of a given [name].
	 */
	operator fun set(name: String, color: Color): Unit = gles.uniformColor(name.uniformLocation, color)

	/**
	 * Draws a [mesh] using the shader [program].
	 */
	fun drawMesh(mesh: Mesh) {
		val positionBuffer = vertexPositionAttributeName?.attributeLocation?.set(mesh.positions.toDirectBufferAugmented(), 4)
		val textureCoordinatesBuffer = vertexTextureCoordinatesAttributeName?.attributeLocation?.set(mesh.textureCoordinates.toDirectBuffer(), 2)
		val normalBuffer = vertexNormalAttributeName?.attributeLocation?.set(mesh.normals.toDirectBufferAugmented(), 4)

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
	fun dispose() {
		program.delete()
	}
}
