package glimpse.gles.delegates

import kotlin.reflect.KProperty

class EnumPairSetAndRememberDelegate<E : Enum<E>, T>(var value: Pair<E, E>, val map: Map<E, T>, val setter: (Pair<T, T>) -> Unit) {

	operator fun getValue(thisRef: Any?, property: KProperty<*>): Pair<E, E> = value

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Pair<E, E>) {
		setter(map[value.first]!! to map[value.second]!!)
		this.value = value
	}
}
