package glimpse.jogl.gles.delegates

import javax.media.opengl.GL2ES2
import kotlin.reflect.KProperty

internal class EnableDisableDelegate(val gles: GL2ES2, val key: Int) {

	operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
			gles.glIsEnabled(key)

	operator fun setValue(thisRef: Any?, property: KProperty<*>, enabled: Boolean) =
			if (enabled) gles.glEnable(key)
			else gles.glDisable(key)
}
