package glimpse.jogl.gles.delegates

import javax.media.opengl.GL2ES2
import kotlin.reflect.KProperty

/**
 * GLES enable/disable flag property delegate.
 *
 * @property gles GLES implementation.
 * @property key GLES enable/disable flag key.
 */
internal class EnableDisableDelegate(val gles: GL2ES2, val key: Int) {

	/**
	 * Gets delegated property value.
	 */
	operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
			gles.glIsEnabled(key)

	/**
	 * Sets delegated property value.
	 */
	operator fun setValue(thisRef: Any?, property: KProperty<*>, enabled: Boolean) =
			if (enabled) gles.glEnable(key)
			else gles.glDisable(key)
}
