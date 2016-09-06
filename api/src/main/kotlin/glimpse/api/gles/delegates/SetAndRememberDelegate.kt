package glimpse.api.gles.delegates

import kotlin.reflect.KProperty

class SetAndRememberDelegate<T>(var value: T, val setter: (T) -> Unit) {

	operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		setter(value)
		this.value = value
	}
}
