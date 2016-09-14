package glimpse.gles.delegates

import glimpse.test.GlimpseSpec

class EnumSetAndRememberDelegateSpec : GlimpseSpec() {

	init {

		"Enum set-and-remember delegate" should {
			"return correct initial value" {
				val obj = ClassWithDelegate()
				obj.value shouldBe TestEnum.ENUM_VALUE_1
			}
			"call a setter lambda" {
				val obj = ClassWithDelegate()
				forAll(TestEnum.values()) { value ->
					obj.value = value
					obj.realValue shouldBe value.intValue
				}
			}
			"remember the value" {
				val obj = ClassWithDelegate()
				forAll(TestEnum.values()) { value ->
					obj.value = value
					obj.value shouldBe value
				}
			}
		}

	}

	enum class TestEnum {
		ENUM_VALUE_1,
		ENUM_VALUE_2,
		ENUM_VALUE_3;

		val intValue = (ordinal + 1) * 10
	}

	class ClassWithDelegate {
		val testEnumMapping = TestEnum.values().map { it to it.intValue }.toMap()
		var realValue: Int = 10
		var value: TestEnum by EnumSetAndRememberDelegate(TestEnum.ENUM_VALUE_1, testEnumMapping) { realValue = it }
	}
}