package glimpse.test

import io.kotlintest.matchers.Matcher

interface FloatMatchers {
	infix fun Float.plusOrMinus(tolerance: Float): ToleranceMatcher = ToleranceMatcher(this, tolerance)

	fun exactly(d: Float): Matcher<Float> = object : Matcher<Float> {
		override fun test(value: Float) {
			if (value != d)
				throw AssertionError("$value is not equal to expected value $d")
		}
	}
}

class ToleranceMatcher(val expected: Float, val tolerance: Float) : Matcher<Float> {

	override fun test(value: Float) {
		if (tolerance == 0.0f)
			println("[WARN] When comparing floats consider using tolerance, eg: a shouldBe b plusOrMinus c")
		val diff = Math.abs(value - expected)
		if (diff > tolerance)
			throw AssertionError("$value is not equal to $expected")
	}

	infix fun plusOrMinus(tolerance: Float): ToleranceMatcher = ToleranceMatcher(expected, tolerance)
}
