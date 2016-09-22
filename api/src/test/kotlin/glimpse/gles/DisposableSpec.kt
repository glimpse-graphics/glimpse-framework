package glimpse.gles

import com.nhaarman.mockito_kotlin.*
import glimpse.test.GlimpseSpec

class DisposableSpec : GlimpseSpec() {

	init {

		"Disposable object" should {
			"be disposed if registered by calling `Disposables.register()`" {
				val disposable = disposableMock()
				Disposables.register(disposable)
				Disposables.disposeAll()
				verify(disposable).dispose()
			}
			"be disposed if registered by calling `registerDisposable()`" {
				val disposable = disposableMock()
				disposable.registerDisposable()
				Disposables.disposeAll()
				verify(disposable).dispose()
			}
			"not be disposed if not registered" {
				val disposable = disposableMock()
				Disposables.disposeAll()
				verify(disposable, never()).dispose()
			}
			"be disposed only once" {
				val disposable = disposableMock()
				disposable.registerDisposable()
				repeat(10) { Disposables.disposeAll() }
				verify(disposable, times(1)).dispose()
			}
		}

	}

	fun disposableMock(): Disposable {
		val disposableMock = mock<DisposableImpl>()
		doCallRealMethod().`when`(disposableMock).registerDisposable()
		return disposableMock
	}

	open class DisposableImpl : Disposable {
		override fun dispose() {
		}
	}
}
