package glimpse.gles.delegates

import glimpse.gles.Disposable
import kotlin.reflect.KProperty

/**
 * Disposable lazy property delegate.
 *
 * @param T Property type.
 * @property init Lazy initialization function.
 */
class DisposableLazyDelegate<T>(private val init: () -> T) : Disposable {

	private var value: T? = null

	/**
	 * Gets delegated property value.
	 */
	operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
		if (value == null) {
			value = init()
			registerDisposable()
		}
		return value!!
	}

	/**
	 * Disposes property value.
	 */
	override fun dispose() {
		value = null
	}
}
