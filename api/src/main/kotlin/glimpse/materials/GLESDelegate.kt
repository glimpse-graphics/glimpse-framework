package glimpse.materials

import glimpse.gles.GLES
import kotlin.reflect.KProperty

internal class GLESDelegate {

	private var gles: GLES? = null

	operator fun getValue(thisRef: Any?, property: KProperty<*>): GLES = gles ?: throw IllegalStateException("GLES not initialized")

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: GLES) {
		if (gles == null) gles = value
	}
}