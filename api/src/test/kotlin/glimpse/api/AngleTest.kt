package glimpse.api

import org.junit.Test
import kotlin.test.expect

class AngleTest {

	@Test
	fun angleFromDeg() {
		val angle = Angle.fromDeg(45f)
		expect(45f) { angle.deg }
		expect(0.7853982f) { angle.rad }
	}

	@Test
	fun angleFromRad() {
		val angle = Angle.fromRad(2f)
		expect(114.59156f) { angle.deg }
		expect(2f) { angle.rad }
	}

	@Test
	fun angleToString() {
		expect("30.0°") { Angle.fromDeg(30f).toString() }
	}

	@Test
	fun arithmeticOperations() {
		expect(Angle.fromDeg(-30f)) { -Angle.fromDeg(30f) }
		expect(Angle.RIGHT) { Angle.fromDeg(30f) + Angle.fromDeg(60f) }
		expect(Angle.fromDeg(60f)) { Angle.RIGHT - Angle.fromDeg(30f) }
		expect(Angle.fromDeg(60f)) { Angle.fromDeg(30f) * 2f }
		expect(Angle.fromDeg(15f)) { Angle.fromDeg(30f) / 2f }
		expect(2f) { Angle.fromDeg(30f) / Angle.fromDeg(15f) }
		expect(Angle.fromDeg(15f)) { Angle.fromDeg(45f) % Angle.fromDeg(30f) }
		expect(Angle.fromDeg(15f).rad) { (Angle.fromDeg(45f) % Angle.fromDeg(30f)).rad }
	}

	@Test
	fun coterminalClockwise() {
		expect(Angle.fromDeg(450f)) { Angle.RIGHT clockwiseFrom Angle.fromDeg(630f) }
		expect(Angle.RIGHT) { Angle.RIGHT clockwiseFrom Angle.RIGHT }
		expect(Angle.fromDeg(-270f)) { Angle.RIGHT clockwiseFrom Angle.NULL }
		expect(Angle.NULL) { Angle.fromDeg(360f) clockwiseFrom Angle.RIGHT }
	}

	@Test
	fun coterminalCounterClockwise() {
		expect(Angle.RIGHT) { Angle.RIGHT counterClockwiseFrom Angle.RIGHT }
		expect(Angle.RIGHT) { Angle.RIGHT counterClockwiseFrom Angle.NULL }
		expect(Angle.fromDeg(-270f)) { Angle.RIGHT counterClockwiseFrom Angle.fromDeg(-360f) }
		expect(Angle.fromDeg(540f)) { Angle.STRAIGHT counterClockwiseFrom Angle.fromDeg(270f) }
	}

	@Test
	fun sortedListOfAngles() {
		expect(listOf(Angle.fromDeg(-30f), Angle.fromDeg(0f), Angle.fromDeg(30f), Angle.fromDeg(60f))) {
			listOf(Angle.fromDeg(0f), Angle.fromDeg(60f), Angle.fromDeg(-30f), Angle.fromDeg(30f)).sorted()
		}
	}

	@Test
	fun setOfAngles() {
		expect(setOf(Angle.fromDeg(0f), Angle.fromDeg(30f), Angle.fromDeg(60f))) {
			setOf(Angle.fromDeg(0f), Angle.fromDeg(30f), Angle.fromDeg(30f), Angle.fromDeg(60f))
		}
	}
}