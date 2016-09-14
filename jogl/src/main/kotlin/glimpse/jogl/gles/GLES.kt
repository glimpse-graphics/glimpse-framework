package glimpse.jogl.gles

import com.jogamp.opengl.util.texture.TextureIO
import glimpse.Color
import glimpse.gles.*
import glimpse.gles.GLES
import glimpse.gles.delegates.EnumPairSetAndRememberDelegate
import glimpse.gles.delegates.EnumSetAndRememberDelegate
import glimpse.gles.delegates.SetAndRememberDelegate
import glimpse.jogl.gles.delegates.EnableDisableDelegate
import glimpse.shaders.ProgramHandle
import glimpse.shaders.ShaderHandle
import glimpse.shaders.ShaderType
import glimpse.textures.*
import java.io.InputStream
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer
import javax.media.opengl.GL2ES2

/**
 * JOGL implementation of GLES facade.
 *
 * @property gles JOGL GL2ES2 implementation.
 */
class GLES(val gles: GL2ES2) : GLES {

	override var viewport: Viewport by SetAndRememberDelegate(Viewport(1, 1)) {
		gles.glViewport(0, 0, it.width, it.height)
	}

	override var clearColor: Color by SetAndRememberDelegate(Color.Companion.BLACK) {
		gles.glClearColor(it.red, it.green, it.blue, it.alpha)
	}

	override var clearDepth: Float by SetAndRememberDelegate(1f) {
		gles.glClearDepthf(it)
	}

	override var isDepthTest: Boolean by EnableDisableDelegate(gles, GL2ES2.GL_DEPTH_TEST)
	override var depthTestFunction: DepthTestFunction by EnumSetAndRememberDelegate(DepthTestFunction.LESS, depthTestFunctionMapping) {
		gles.glDepthFunc(it)
	}

	override var isBlend: Boolean by EnableDisableDelegate(gles, GL2ES2.GL_BLEND)
	override var blendFunction: Pair<BlendFactor, BlendFactor> by EnumPairSetAndRememberDelegate(BlendFactor.ZERO to BlendFactor.ONE, blendFactorMapping) {
		gles.glBlendFunc(it.first, it.second)
	}

	override var isCullFace: Boolean by EnableDisableDelegate(gles, GL2ES2.GL_CULL_FACE)
	override var cullFaceMode: CullFaceMode by EnumSetAndRememberDelegate(CullFaceMode.BACK, cullFaceModeMapping) {
		gles.glCullFace(it)
	}

	override fun clearDepthBuffer() {
		gles.glClear(GL2ES2.GL_DEPTH_BUFFER_BIT)
	}

	override fun clearColorBuffer() {
		gles.glClear(GL2ES2.GL_COLOR_BUFFER_BIT)
	}

	override fun createShader(shaderType: ShaderType) =
			ShaderHandle(gles.glCreateShader(shaderTypeMapping[shaderType]!!))

	override fun compileShader(shaderHandle: ShaderHandle, source: String) {
		val sources = arrayOf(source)
		gles.glShaderSource(shaderHandle.value, 1, sources, sources.map { it.length }.toIntArray(), 0)
		gles.glCompileShader(shaderHandle.value)
	}

	override fun deleteShader(shaderHandle: ShaderHandle) {
		gles.glDeleteShader(shaderHandle.value)
	}

	override fun getShaderCompileStatus(shaderHandle: ShaderHandle) =
			getShaderProperty(shaderHandle, GL2ES2.GL_COMPILE_STATUS) == GL2ES2.GL_TRUE

	private fun getShaderProperty(shaderHandle: ShaderHandle, property: Int): Int {
		val result = IntBuffer.allocate(1)
		gles.glGetShaderiv(shaderHandle.value, property, result)
		return result[0]
	}

	override fun getShaderLog(shaderHandle: ShaderHandle): String {
		val length = getShaderProperty(shaderHandle, GL2ES2.GL_INFO_LOG_LENGTH)
		val logLength = IntBuffer.allocate(1)
		val log = ByteBuffer.allocate(length)
		gles.glGetShaderInfoLog(shaderHandle.value, length, logLength, log)
		return String(log.array())
	}

	override fun createProgram() =
			ProgramHandle(gles.glCreateProgram())

	override fun attachShader(programHandle: ProgramHandle, shaderHandle: ShaderHandle) {
		gles.glAttachShader(programHandle.value, shaderHandle.value)
	}

	override fun linkProgram(programHandle: ProgramHandle) {
		gles.glLinkProgram(programHandle.value)
	}

	override fun useProgram(programHandle: ProgramHandle) {
		gles.glUseProgram(programHandle.value)
	}

	override fun deleteProgram(programHandle: ProgramHandle) {
		gles.glDeleteProgram(programHandle.value)
	}

	override fun getProgramLinkStatus(programHandle: ProgramHandle) =
			getProgramProperty(programHandle, GL2ES2.GL_LINK_STATUS) == GL2ES2.GL_TRUE

	private fun getProgramProperty(programHandle: ProgramHandle, property: Int): Int {
		val result = IntBuffer.allocate(1)
		gles.glGetProgramiv(programHandle.value, property, result)
		return result[0]
	}

	override fun getProgramLog(programHandle: ProgramHandle): String {
		val length = getProgramProperty(programHandle, GL2ES2.GL_INFO_LOG_LENGTH)
		val logLength = IntBuffer.allocate(1)
		val log = ByteBuffer.allocate(length)
		gles.glGetProgramInfoLog(programHandle.value, length, logLength, log)
		return String(log.array())
	}

	override fun getUniformLocation(handle: ProgramHandle, name: String) =
			UniformLocation(gles.glGetUniformLocation(handle.value, name))

	override fun getAttributeLocation(handle: ProgramHandle, name: String) =
			AttributeLocation(gles.glGetAttribLocation(handle.value, name))

	override fun uniformFloat(location: UniformLocation, float: Float) {
		gles.glUniform1f(location.value, float)
	}

	override fun uniformInt(location: UniformLocation, int: Int) {
		gles.glUniform1i(location.value, int)
	}

	override fun uniformMatrix16f(location: UniformLocation, _16f: Array<Float>) {
		gles.glUniformMatrix4fv(location.value, 1, false, _16f.toFloatArray(), 0)
	}

	override fun uniform4f(location: UniformLocation, _4f: Array<Float>) {
		gles.glUniform4fv(location.value, 1, _4f.toFloatArray(), 0)
	}

	override fun createAttributeFloatArray(location: AttributeLocation, buffer: FloatBuffer, vectorSize: Int) =
			createAttributeArray(location, buffer, vectorSize, GL2ES2.GL_FLOAT, 4L)

	override fun createAttributeIntArray(location: AttributeLocation, buffer: IntBuffer, vectorSize: Int) =
			createAttributeArray(location, buffer, vectorSize, GL2ES2.GL_INT, 4L)

	private fun createAttributeArray(location: AttributeLocation, buffer: Buffer, vectorSize: Int, type: Int, typeSize: Long): BufferHandle {
		buffer.rewind()
		val handles = IntArray(1)
		gles.glGenBuffers(1, handles, 0)
		gles.glBindBuffer(GL2ES2.GL_ARRAY_BUFFER, handles[0])
		gles.glBufferData(GL2ES2.GL_ARRAY_BUFFER, buffer.limit().toLong() * typeSize, buffer, GL2ES2.GL_STATIC_DRAW)
		gles.glVertexAttribPointer(location.value, vectorSize, type, false, 0, 0L)
		return BufferHandle(handles[0])
	}

	override fun deleteAttributeArray(handle: BufferHandle) {
		gles.glDeleteBuffers(1, arrayOf(handle.value).toIntArray(), 0)
	}

	override fun enableAttributeArray(location: AttributeLocation) {
		gles.glEnableVertexAttribArray(location.value)
	}

	override fun disableAttributeArray(location: AttributeLocation) {
		gles.glDisableVertexAttribArray(location.value)
	}

	override var textureMinificationFilter: TextureMinificationFilter
			by EnumSetAndRememberDelegate(TextureMinificationFilter.NEAREST_MIPMAP_LINEAR, textureMinificationFilterMapping) {
				gles.glTexParameteri(GL2ES2.GL_TEXTURE_2D, GL2ES2.GL_TEXTURE_MIN_FILTER, it)
			}
	override var textureMagnificationFilter: TextureMagnificationFilter
			by EnumSetAndRememberDelegate(TextureMagnificationFilter.LINEAR, textureMagnificationFilterMapping) {
				gles.glTexParameteri(GL2ES2.GL_TEXTURE_2D, GL2ES2.GL_TEXTURE_MIN_FILTER, it)
			}
	override var textureWrapping: Pair<TextureWrapping, TextureWrapping>
			by EnumPairSetAndRememberDelegate(TextureWrapping.REPEAT to TextureWrapping.REPEAT, textureWrappingMapping) {
				gles.glTexParameteri(GL2ES2.GL_TEXTURE_2D, GL2ES2.GL_TEXTURE_WRAP_S, it.first)
				gles.glTexParameteri(GL2ES2.GL_TEXTURE_2D, GL2ES2.GL_TEXTURE_WRAP_T, it.second)
			}

	override fun generateTextures(count: Int): Array<TextureHandle> {
		val handles = IntArray(count)
		gles.glGenTextures(count, handles, 0)
		return handles.map { TextureHandle(it) }.toTypedArray()
	}

	override fun deleteTextures(count: Int, handles: Array<TextureHandle>) {
		gles.glDeleteTextures(count, handles.map { it.value }.toIntArray(), 0)
	}

	override fun bindTexture2D(handle: TextureHandle) {
		gles.glBindTexture(GL2ES2.GL_TEXTURE_2D, handle.value)
	}

	override fun textureImage2D(inputStream: InputStream, fileName: String, withMipmap: Boolean) {
		val fileType = "." + fileName.split('.').last().toLowerCase()
		val textureData = TextureIO.newTextureData(gles.glProfile, inputStream, withMipmap, fileType)
		gles.glTexImage2D(GL2ES2.GL_TEXTURE_2D, 0, GL2ES2.GL_RGBA,
				textureData.getWidth(), textureData.getHeight(), 0,
				GL2ES2.GL_RGBA, GL2ES2.GL_UNSIGNED_BYTE, textureData.getBuffer())
		if (withMipmap) gles.glGenerateMipmap(GL2ES2.GL_TEXTURE_2D)
	}

	override fun activeTexture(index: Int) {
		require(index in 0..31) { "Texture index out of bounds: $index" }
		gles.glActiveTexture(GL2ES2.GL_TEXTURE0 + index)
	}

	override fun drawTriangles(verticesCount: Int) {
		gles.glDrawArrays(GL2ES2.GL_TRIANGLES, 0, verticesCount)
	}
}
