package glimpse

import glimpse.buffers.toDirectFloatBuffer

/**
 * Texture coordinates.
 *
 * @property u U coordinate.
 * @property v V coordinate.
 */
data class TextureCoordinates(val u: Float, val v: Float) {

	internal val _2f: Array<Float> by lazy { arrayOf(u, v) }

	/**
	 * Returns a string representation of the [TextureCoordinates].
	 */
	override fun toString() = "(%.1f, %.1f)".format(u, v)
}

/**
 * Returns a direct buffer containing values of UV coordinates in the original list.
 */
fun List<TextureCoordinates>.toDirectBuffer() = toDirectFloatBuffer(size * 2) { it._2f }
