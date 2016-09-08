package glimpse.gles.delegates

import kotlin.reflect.KProperty

class EnumSetAndRememberDelegate<E : Enum<E>, T>(var value: E, val map: Map<E, T>, val setter: (T) -> Unit) {

	operator fun getValue(thisRef: Any?, property: KProperty<*>): E = value

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: E) {
		setter(map[value]!!)
		this.value = value
	}
}
