package glimpse.gles.delegates

import glimpse.test.GlimpseSpec

class SetAndRememberDelegateSpec : GlimpseSpec() {

	init {

		"Set-and-remember delegate" should {
			"return correct initial value" {
				val obj = ClassWithDelegate()
				obj.value shouldBe 0
			}
			"call a setter lambda" {
				val obj = ClassWithDelegate()
				forAll(integers) { integer ->
					obj.value = integer
					obj.realValue == integer
				}
			}
			"remember the value" {
				val obj = ClassWithDelegate()
				forAll(integers) { integer ->
					obj.value = integer
					obj.value == integer
				}
			}
		}

	}

	class ClassWithDelegate {
		var realValue: Int = 0
		var value: Int by SetAndRememberDelegate(0) { realValue = it }
	}
}