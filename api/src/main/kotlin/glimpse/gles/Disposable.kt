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
		disposables.forEach { it.dispose() }
		disposables.clear()
	}
}
