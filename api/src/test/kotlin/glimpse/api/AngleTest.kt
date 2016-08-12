package glimpse.api

import org.junit.Test
import kotlin.test.expect

class AngleTest {

	@Test
	fun angleFromDeg() {
		val angle = 45.degrees
		expect(45f) { angle.deg }
		expect(0.7853982f) { angle.rad }
	}

	@Test
	fun angleFromRad() {
		val angle = 2.radians
		expect(114.59156f) { angle.deg }
		expect(2f) { angle.rad }
	}

	@Test
	fun angleToString() {
		expect("30.0°") { 30.degrees.toString() }
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
	fun trigonometricOperations() {
		expect(sin(90.degrees)) { 1.0f }
		expect(cos(0.degrees)) { 1.0f }
		expect(tan(45.degrees)) { 1.0f }
		expect(atan2(1f, 1f)) { 45.degrees }
	}

	@Test
	fun coterminalClockwise() {
		expect(450.degrees) { Angle.RIGHT clockwiseFrom 630.degrees }
		expect(Angle.RIGHT) { Angle.RIGHT clockwiseFrom Angle.RIGHT }
		expect(-270.degrees) { Angle.RIGHT clockwiseFrom Angle.NULL }
		expect(Angle.NULL) { 360.degrees clockwiseFrom Angle.RIGHT }
	}

	@Test
	fun coterminalCounterClockwise() {
		expect(Angle.RIGHT) { Angle.RIGHT counterClockwiseFrom Angle.RIGHT }
		expect(Angle.RIGHT) { Angle.RIGHT counterClockwiseFrom Angle.NULL }
		expect(-270.degrees) { Angle.RIGHT counterClockwiseFrom -360.degrees }
		expect(540.degrees) { Angle.STRAIGHT counterClockwiseFrom 270.degrees }
	}

	@Test
	fun sortedListOfAngles() {
		expect(listOf(-30.degrees, 0.degrees, 30.degrees, 60.degrees)) {
			listOf(0.degrees, 60.degrees, -30.degrees, 30.degrees).sorted()
		}
	}

	@Test
	fun setOfAngles() {
		expect(setOf(0.degrees, 30.degrees, 60.degrees)) {
			setOf(0.degrees, 30.degrees, 30.degrees, 60.degrees)
		}
	}
}