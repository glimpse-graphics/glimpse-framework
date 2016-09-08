package glimpse.gles.delegates

import kotlin.reflect.KProperty

/**
 * GLES delegate remembering and converting an enum property [value] and calling additional [setter] lambda.
 *
 * @param E Enum type.
 * @param T Converted enum value type.
 *
 * @property value Delegated property value.
 * @property mapping Enum to value mapping.
 * @property setter Setter lambda.
 */
class EnumSetAndRememberDelegate<E : Enum<E>, T>(var value: E, val mapping: Map<E, T>, val setter: (T) -> Unit) {

	/**
	 * Gets delegated property value.
	 */
	operator fun getValue(thisRef: Any?, property: KProperty<*>): E = value

	/**
	 * Sets delegated property value.
	 */
	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: E) {
		setter(mapping[value]!!)
		this.value = value
	}
}
