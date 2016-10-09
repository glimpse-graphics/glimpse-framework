package glimpse.textures

import glimpse.gles.GLES
import glimpse.io.Resource
import glimpse.gles.delegates.GLESDelegate
import java.io.InputStream

/**
 * Texture builder.
 */
class TextureBuilder {

	private val gles: GLES by GLESDelegate

	/**
	 * Name of the texture.
	 */
	var name: String = ""

	internal var isMipmap: Boolean = false

	/**
	 * Sets texture with [mipmap].
	 */
	fun withMipmap() {
		isMipmap = true
	}

	/**
	 * Sets texture with [mipmap].
	 */
	infix fun <T> T.with(mipmap: mipmap): T {
		withMipmap()
		return this
	}

	/**
	 * Sets texture without [mipmap].
	 */
	fun withoutMipmap() {
		isMipmap = false
	}

	/**
	 * Sets texture without [mipmap].
	 */
	infix fun <T> T.without(mipmap: mipmap): T {
		withoutMipmap()
		return this
	}

	internal fun build(inputStream: InputStream): Texture {
		val handle = gles.generateTexture()
		gles.bindTexture2D(handle)
		gles.textureImage2D(inputStream, name, isMipmap)
		return Texture(handle)
	}
}

/**
 * Mipmap indicator.
 */
object mipmap

/**
 * Builds texture initialized with an [init] function.
 */
fun InputStream.loadTexture(init: TextureBuilder.() -> Unit = {}): Texture {
	val builder = TextureBuilder()
	builder.init()
	val texture = builder.build(this)
	close()
	return texture
}

/**
 * Builds texture initialized with an [init] function.
 */
fun Resource.loadTexture(init: TextureBuilder.() -> Unit = {}): Texture =
		inputStream.loadTexture {
			name = this@loadTexture.name
			init()
		}
