package glimpse.gles

import glimpse.Color
import glimpse.Matrix
import glimpse.Point
import glimpse.Vector
import glimpse.shaders.ProgramHandle
import glimpse.shaders.ShaderHandle
import glimpse.shaders.ShaderType
import java.io.InputStream
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * GLES facade interface.
 */
interface GLES {

	/**
	 * Rendering viewport.
	 */
	var viewport: Viewport

	/**
	 * Clear color value.
	 */
	var clearColor: Color

	/**
	 * Clear depth value.
	 */
	var clearDepth: Float

	/**
	 * Depth test status.
	 */
	var isDepthTest: Boolean

	/**
	 * Depth test function.
	 */
	var depthTestFunction: DepthTestFunction

	/**
	 * Blending status.
	 */
	var isBlend: Boolean

	/**
	 * Blending function.
	 * First element indicates source blending factor.
	 * Second element indicates destination blending factor.
	 */
	var blendFunction: Pair<BlendFactor, BlendFactor>

	/**
	 * Face culling status.
	 */
	var isCullFace: Boolean

	/**
	 * Face culling mode.
	 */
	var cullFaceMode: CullFaceMode

	/**
	 * Clears depth buffer.
	 * Further rendering will be done on top of rendered image.
	 */
	fun clearDepthBuffer(): Unit

	/**
	 * Clears color buffer.
	 * The [viewport] will be filled with the [clearColor].
	 */
	fun clearColorBuffer(): Unit

	/**
	 * Creates an empty [ShaderHandle].
	 */
	fun createShader(shaderType: ShaderType): ShaderHandle

	/**
	 * Compiles a shader from [source].
	 */
	fun compileShader(shaderHandle: ShaderHandle, source: String): Unit

	/**
	 * Deletes a shader.
	 */
	fun deleteShader(shaderHandle: ShaderHandle): Unit

	/**
	 * Gets shader compilation status.
	 */
	fun getShaderCompileStatus(shaderHandle: ShaderHandle): Boolean

	/**
	 * Gets shader compilation log.
	 */
	fun getShaderLog(shaderHandle: ShaderHandle): String

	/**
	 * Creates an empty [ProgramHandle].
	 */
	fun createProgram(): ProgramHandle

	/**
	 * Adds a shader to a program.
	 */
	fun attachShader(programHandle: ProgramHandle, shaderHandle: ShaderHandle): Unit

	/**
	 * Links a shader program.
	 */
	fun linkProgram(programHandle: ProgramHandle): Unit

	/**
	 * Starts using given shader program.
	 */
	fun useProgram(programHandle: ProgramHandle): Unit

	/**
	 * Deletes a shader program.
	 */
	fun deleteProgram(programHandle: ProgramHandle): Unit

	/**
	 * Gets shader program linking status.
	 */
	fun getProgramLinkStatus(programHandle: ProgramHandle): Boolean

	/**
	 * Gets shader program linking log.
	 */
	fun getProgramLog(programHandle: ProgramHandle): String

	/**
	 * Gets a location of a shader uniform with the given [name].
	 */
	fun getUniformLocation(handle: ProgramHandle, name: String): UniformLocation

	/**
	 * Gets a location of a shader attribute with the given [name].
	 */
	fun getAttributeLocation(handle: ProgramHandle, name: String): AttributeLocation

	/**
	 * Sets a uniform [float] value.
	 */
	fun uniformFloat(location: UniformLocation, float: Float): Unit

	/**
	 * Sets a uniform [int] value.
	 */
	fun uniformInt(location: UniformLocation, int: Int): Unit

	/**
	 * Sets a uniform [matrix] value.
	 */
	fun uniformMatrix(location: UniformLocation, matrix: Matrix): Unit = uniformMatrix16f(location, matrix._16f)

	/**
	 * Sets a uniform `mat4` value.
	 */
	fun uniformMatrix16f(location: UniformLocation, _16f: Array<Float>): Unit

	/**
	 * Sets a uniform [vector] value.
	 */
	fun uniformVector(location: UniformLocation, vector: Vector): Unit = uniform4f(location, vector._4f)

	/**
	 * Sets a uniform [point] value.
	 */
	fun uniformPoint(location: UniformLocation, point: Point): Unit = uniform4f(location, point._4f)

	/**
	 * Sets a uniform [color] value.
	 */
	fun uniformColor(location: UniformLocation, color: Color): Unit = uniform4f(location, color._4f)

	/**
	 * Sets a uniform `vec4` value.
	 */
	fun uniform4f(location: UniformLocation, _4f: Array<Float>): Unit

	/**
	 * Creates a shader attribute buffer with floats arranged in vectors of [vectorSize].
	 */
	fun createAttributeFloatArray(location: AttributeLocation, buffer: FloatBuffer, vectorSize: Int): BufferHandle

	/**
	 * Creates a shader attribute buffer with integers arranged in vectors of [vectorSize].
	 */
	fun createAttributeIntArray(location: AttributeLocation, buffer: IntBuffer, vectorSize: Int): BufferHandle

	/**
	 * Deletes a shader attribute buffer.
	 */
	fun deleteAttributeArray(handle: BufferHandle): Unit

	/**
	 * Enables given attribute.
	 */
	fun enableAttributeArray(location: AttributeLocation): Unit

	/**
	 * Disables given attribute.
	 */
	fun disableAttributeArray(location: AttributeLocation): Unit

	/**
	 * Texture minification filter.
	 */
	var textureMinificationFilter: TextureMinificationFilter

	/**
	 * Texture magnification filter.
	 */
	var textureMagnificationFilter: TextureMagnificationFilter

	/**
	 * Texture wrapping strategy along U and V coordinates.
	 */
	var textureWrapping: Pair<TextureWrapping, TextureWrapping>

	/**
	 * Generates an empty [TextureHandle].
	 */
	fun generateTexture(): TextureHandle = generateTextures(1)[0]

	/**
	 * Generates an array of empty [TextureHandle]s.
	 */
	fun generateTextures(count: Int): Array<TextureHandle>

	/**
	 * Deletes given texture.
	 */
	fun deleteTexture(handle: TextureHandle): Unit = deleteTextures(1, arrayOf(handle))

	/**
	 * Deletes given textures.
	 */
	fun deleteTextures(count: Int, handles: Array<TextureHandle>): Unit

	/**
	 * Binds a 2-dimensional texture.
	 */
	fun bindTexture2D(handle: TextureHandle): Unit

	/**
	 * Loads a 2-dimensional texture image from [inputStream].
	 *
	 * @param inputStream Image input stream.
	 * @param fileName Image file name with extension.
	 * @param withMipmap Mipmapping mode.
	 */
	fun textureImage2D(inputStream: InputStream, fileName: String, withMipmap: Boolean): Unit

	/**
	 * Selects an [index] of an active texture.
	 */
	fun activeTexture(index: Int): Unit

	/**
	 * Draws triangles.
	 */
	fun drawTriangles(verticesCount: Int): Unit
}
