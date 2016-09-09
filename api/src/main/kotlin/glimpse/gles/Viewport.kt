package glimpse.gles

/**
 * Rendering viewport.
 *
 * @property width Viewport width.
 * @property height Viewport height.
 */
data class Viewport(val width: Int, val height: Int) {

	/**
	 * Viewport aspect ratio.
	 */
	val aspect: Float by lazy { width.toFloat() / height.toFloat() }

	/**
	 * Returns a string representation of the [Viewport].
	 */
	override fun toString(): String = "$width×$height"
}
