package glimpse.gles.delegates

import glimpse.test.GlimpseSpec

class EnumPairSetAndRememberDelegateSpec : GlimpseSpec() {

	init {

		"Enum set-and-remember delegate" should {
			"return correct initial value" {
				val obj = ClassWithDelegate()
				obj.value.first shouldBe TestEnum.ENUM_VALUE_1
				obj.value.second shouldBe TestEnum.ENUM_VALUE_2
			}
			"call a setter lambda" {
				val obj = ClassWithDelegate()
				forAll(TestEnum.values()) { first ->
					forAll(TestEnum.values()) { second ->
						obj.value = first to second
						obj.firstRealValue shouldBe first.intValue
						obj.secondRealValue shouldBe second.intValue
					}
				}
			}
			"remember the value" {
				val obj = ClassWithDelegate()
				forAll(TestEnum.values()) { first ->
					forAll(TestEnum.values()) { second ->
						obj.value = first to second
						obj.value.first shouldBe first
						obj.value.second shouldBe second
					}
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
		var firstRealValue: Int = 10
		var secondRealValue: Int = 20
		var value: Pair<TestEnum, TestEnum> by EnumPairSetAndRememberDelegate(TestEnum.ENUM_VALUE_1 to TestEnum.ENUM_VALUE_2, testEnumMapping) {
			firstRealValue = it.first
			secondRealValue = it.second
		}
	}
}