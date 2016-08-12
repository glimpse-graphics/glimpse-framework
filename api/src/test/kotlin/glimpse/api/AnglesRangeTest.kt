package glimpse.api

import org.junit.Test
import kotlin.test.expect

class AnglesRangeTest {

	@Test
	fun partitioning() {
		expect(listOf(0.degrees, 45.degrees, 90.degrees, 135.degrees, 180.degrees)) { Angle.NULL..Angle.STRAIGHT partition 4 }
	}
}