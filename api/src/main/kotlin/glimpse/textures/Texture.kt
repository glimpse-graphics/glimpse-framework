package glimpse.textures

import glimpse.gles.GLES
import glimpse.gles.UniformLocation

/**
 * Texture.
 *
 * @property gles GLES implementation.
 * @property handle Texture handle.
 * @property index Texture index in rendering.
 */
class Texture(val gles: GLES, val handle: TextureHandle) {

	internal var deleted = false

	/**
	 * Applies a [Texture] with the given [index] to a uniform [location] of a shader program.
	 */
	fun apply(location: UniformLocation, index: Int) {
		gles.activeTexture(index)
		gles.bindTexture2D(handle)
		gles.uniformInt(location, index)
	}

	/**
	 * Deletes the [Texture].
	 */
	fun delete() {
		deleted = true
		gles.deleteTexture(handle)
	}
}

/**
 * Texture handle.
 *
 * @property value Handle value.
 */
data class TextureHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid texture handle: $value" }
	}
}
