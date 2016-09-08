package glimpse.gles.delegates

import kotlin.reflect.KProperty

/**
 * GLES delegate remembering and converting an enum pair property [value] and calling additional [setter] lambda.
 *
 * @param E Enum type.
 * @param T Converted enum value type.
 *
 * @property value Delegated property value.
 * @property mapping Enum to value mapping.
 * @property setter Setter lambda.
 */
class EnumPairSetAndRememberDelegate<E : Enum<E>, T>(var value: Pair<E, E>, val mapping: Map<E, T>, val setter: (Pair<T, T>) -> Unit) {

	/**
	 * Gets delegated property value.
	 */
	operator fun getValue(thisRef: Any?, property: KProperty<*>): Pair<E, E> = value

	/**
	 * Sets delegated property value.
	 */
	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Pair<E, E>) {
		setter(mapping[value.first]!! to mapping[value.second]!!)
		this.value = value
	}
}
