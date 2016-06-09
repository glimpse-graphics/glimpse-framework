package glimpse.api

import org.junit.Test
import kotlin.test.expect

class ColorTest {

	@Test
	fun colorToString() {
		expect("#ff000000") { Color.BLACK.toString() }
		expect("#ff7f7f7f") { Color.GRAY.toString() }
		expect("#ffffffff") { Color.WHITE.toString() }
	}
}