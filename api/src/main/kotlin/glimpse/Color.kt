package glimpse

import glimpse.buffers.toDirectFloatBuffer

/**
 * RGBA color.
 *
 * @property red Red channel.
 * @property green Green channel.
 * @property blue Blue channel.
 * @property alpha Alpha channel.
 */
data class Color(val red: Float, val green: Float, val blue: Float, val alpha: Float = 1f) {

	companion object {
		val BLACK = Color(0.0f, 0.0f, 0.0f)
		val GRAY = Color(0.5f, 0.5f, 0.5f)
		val WHITE = Color(1.0f, 1.0f, 1.0f)

		val RED = Color(1.0f, 0.0f, 0.0f)
		val GREEN = Color(0.0f, 1.0f, 0.0f)
		val BLUE = Color(0.0f, 0.0f, 1.0f)

		val CYAN = Color(0.0f, 1.0f, 1.0f)
		val MAGENTA = Color(1.0f, 0.0f, 1.0f)
		val YELLOW = Color(1.0f, 1.0f, 0.0f)
	}

	internal val _3f: Array<Float> by lazy { arrayOf(red, green, blue) }
	internal val _4f: Array<Float> by lazy { arrayOf(red, green, blue, alpha) }

	/**
	 * Returns a new [Color] with [newAlpha] channel.
	 */
	infix fun transparent(newAlpha: Float) = Color(red, green, blue, newAlpha)

	/**
	 * Returns a string representation of the [Color].
	 */
	override fun toString() =
			"#%02x%02x%02x%02x".format(
					alpha.toIntChannel(), red.toIntChannel(), green.toIntChannel(), blue.toIntChannel())

	private fun Float.toIntChannel() = (this * 255f).toInt()
}

/**
 * Returns a direct buffer containing values of RGB channels of colors in the original list.
 */
fun List<Color>.toDirectBuffer() = toDirectFloatBuffer(size * 3) { it._3f }

/**
 * Returns a direct buffer containing values of RGBA channels of colors in the original list.
 */
fun List<Color>.toDirectBufferWithAlpha() = toDirectFloatBuffer(size * 4) { it._4f }
