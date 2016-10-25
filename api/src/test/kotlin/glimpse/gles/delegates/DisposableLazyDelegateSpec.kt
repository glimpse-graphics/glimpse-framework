package glimpse.gles.delegates

import glimpse.gles.Disposable
import glimpse.gles.Disposables
import glimpse.test.GlimpseSpec

class DisposableLazyDelegateSpec : GlimpseSpec() {

	init {

		"Disposable lazily delegated property" should {
			"not change after initialization" {
				var number = 1
				class SomeClass {
					val value : Int? by DisposableLazyDelegate { number }
				}
				val instance = SomeClass()

				instance.value shouldBe 1

				forAll(integers) { integer ->
					number = integer
					instance.value == 1
				}
			}
			"change after it was disposed" {
				var number = 1
				class SomeClass {
					val value : Int? by DisposableLazyDelegate { number }
				}
				val instance = SomeClass()

				instance.value shouldBe 1

				forAll(integers) { integer ->
					Disposables.disposeAll()
					number = integer
					instance.value == integer
				}
			}
			"not affect other disposable lazily delegated properties" {
				var number = 1
				class SomeClass {
					val value1 : Int? by DisposableLazyDelegate { number }
					val value2 : Int? by DisposableLazyDelegate { number }
				}
				val instance1 = SomeClass()
				val instance2 = SomeClass()

				instance1.value1 shouldBe 1

				number = 2
				instance1.value1 shouldBe 1
				instance1.value2 shouldBe 2

				number = 3
				instance1.value1 shouldBe 1
				instance1.value2 shouldBe 2
				instance2.value1 shouldBe 3

				number = 4
				instance1.value1 shouldBe 1
				instance1.value2 shouldBe 2
				instance2.value1 shouldBe 3
				instance2.value2 shouldBe 4

				number = 5
				instance1.value1 shouldBe 1
				instance1.value2 shouldBe 2
				instance2.value1 shouldBe 3
				instance2.value2 shouldBe 4
			}
			"not be initialized when disposables are being disposed" {
				class SomeClass : Disposable {

					init {
						registerDisposable()
					}

					val value : Int? by DisposableLazyDelegate { throw AssertionError("Value is being initialized") }

					override fun dispose() {
						value?.inc()
					}
				}

				val instance = SomeClass()

				Disposables.disposeAll()
			}
		}

	}
}
