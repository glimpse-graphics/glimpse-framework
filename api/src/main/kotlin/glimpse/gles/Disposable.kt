package glimpse.gles

/**
 * Disposable GLES-related object.
 */
interface Disposable {

	/**
	 * Disposes the object.
	 */
	fun dispose()

	/**
	 * Registers the [Disposable] object.
	 */
	fun registerDisposable() = Disposables.register(this)
}

/**
 * Container for disposable objects.
 */
object Disposables {

	internal var isDisposing = false

	private val disposables = mutableSetOf<Disposable>()

	/**
	 * Registers a [disposable] object.
	 */
	fun register(disposable: Disposable) {
		disposables.add(disposable)
	}

	/**
	 * Disposes all registered objects.
	 */
	fun disposeAll() {
		isDisposing = true
		disposables.forEach { it.dispose() }
		disposables.clear()
		isDisposing = false
	}
}
