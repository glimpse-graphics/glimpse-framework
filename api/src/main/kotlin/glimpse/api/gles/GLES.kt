package glimpse.api.gles

import glimpse.api.Color
import glimpse.api.Matrix
import glimpse.api.Point
import glimpse.api.Vector
import java.io.InputStream
import java.nio.FloatBuffer
import java.nio.IntBuffer

interface GLES {

	var viewport: Viewport

	var clearColor: Color

	var isDepthTest: Boolean
	var depthTestFunction: DepthTestFunction

	var isBlend: Boolean
	var blendFunction: Pair<BlendFactor, BlendFactor>

	var isCullFace: Boolean
	var cullFaceMode: CullFaceMode

	fun clearDepthBuffer(): Unit
	fun clearColorBuffer(): Unit

	fun createShader(shaderType: ShaderType): ShaderHandle
	fun compileShader(shaderHandle: ShaderHandle, source: String): Unit
	fun deleteShader(shaderHandle: ShaderHandle): Unit

	fun getShaderCompileStatus(shaderHandle: ShaderHandle): Boolean
	fun getShaderLog(shaderHandle: ShaderHandle): String

	fun createProgram(): ProgramHandle
	fun attachShader(programHandle: ProgramHandle, shaderHandle: ShaderHandle): Unit
	fun linkProgram(programHandle: ProgramHandle)
	fun useProgram(programHandle: ProgramHandle)
	fun deleteProgram(programHandle: ProgramHandle)

	fun getProgramLinkStatus(programHandle: ProgramHandle): Boolean
	fun getProgramLog(programHandle: ProgramHandle): String

	fun getUniformLocation(handle: ProgramHandle, name: String): UniformLocation
	fun getAttributeLocation(handle: ProgramHandle, name: String): AttributeLocation

	fun uniformFloat(location: UniformLocation, float: Float): Unit
	fun uniformInt(location: UniformLocation, int: Int): Unit

	fun uniformMatrix(location: UniformLocation, matrix: Matrix): Unit = uniformMatrix16f(location, matrix._16f)

	fun uniformMatrix16f(location: UniformLocation, _16f: Array<Float>): Unit

	fun uniformVector(location: UniformLocation, vector: Vector): Unit = uniform4f(location, vector._4f)
	fun uniformPoint(location: UniformLocation, point: Point): Unit = uniform4f(location, point._4f)
	fun uniformColor(location: UniformLocation, color: Color): Unit = uniform4f(location, color._4f)

	fun uniform4f(location: UniformLocation, _4f: Array<Float>): Unit

	fun createAttributeFloatArray(location: AttributeLocation, buffer: FloatBuffer, size: Int): BufferHandle
	fun createAttributeIntArray(location: AttributeLocation, buffer: IntBuffer, size: Int): BufferHandle

	fun deleteAttributeArray(handle: BufferHandle, name: String): Unit

	fun enableAttributeArray(location: AttributeLocation): Unit
	fun disableAttributeArray(location: AttributeLocation): Unit

	var textureMinificationFilter: TextureMinificationFilter
	var textureMagnificationFilter: TextureMagnificationFilter
	var textureWrapping: Pair<TextureWrapping, TextureWrapping>

	fun generateTexture(): TextureHandle = generateTextures(1)[0]
	fun generateTextures(count: Int): Array<TextureHandle>

	fun deleteTexture(handle: TextureHandle): Unit = deleteTextures(1, arrayOf(handle))
	fun deleteTextures(count: Int, handles: Array<TextureHandle>): Unit

	fun bindTexture2D(handle: TextureHandle): Unit
	fun textureImage2D(inputStream: InputStream, fileName: String, withMipmap: Boolean)
	fun activeTexture(index: Int): Unit

	fun drawTriangles(count: Int): Unit
}
