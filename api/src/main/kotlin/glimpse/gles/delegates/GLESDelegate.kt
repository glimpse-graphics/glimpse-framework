package glimpse.gles.delegates

import glimpse.gles.GLES
import kotlin.reflect.KProperty

/**
 * GLES implementation delegate.
 *
 * Holds singleton instance of GLES.
 */
object GLESDelegate {

	private var gles: GLES? = null

	/**
	 * Gets delegated property value.
	 */
	operator fun getValue(thisRef: Any?, property: KProperty<*>): GLES = gles ?: throw IllegalStateException("GLES not initialized")

	/**
	 * Sets delegated property value.
	 */
	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: GLES) {
		GLESDelegate(value)
	}

	/**
	 * Initializes GLES implementation.
	 */
	operator fun invoke(gles: GLES) {
		GLESDelegate.gles = gles
	}
}