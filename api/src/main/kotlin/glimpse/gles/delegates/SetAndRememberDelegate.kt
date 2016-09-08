package glimpse.gles.delegates

import kotlin.reflect.KProperty

/**
 * GLES delegate remembering property [value] and calling additional [setter] lambda.
 *
 * @property value Delegated property value.
 * @property setter Setter lambda.
 */
class SetAndRememberDelegate<T>(var value: T, val setter: (T) -> Unit) {

	/**
	 * Gets delegated property value.
	 */
	operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

	/**
	 * Sets delegated property value.
	 */
	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		setter(value)
		this.value = value
	}
}
