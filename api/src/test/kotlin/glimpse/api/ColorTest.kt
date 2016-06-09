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

	@Test
	fun colorsToDirectBuffer() {
		val buffer = listOf(Color.GREEN, Color.BLUE, Color.MAGENTA).toDirectBuffer()
		assert(buffer.isDirect)
		val array = FloatArray(9)
		buffer.rewind()
		buffer.get(array)
		expect(listOf(0f, 1f, 0f, 0f, 0f, 1f, 1f, 0f, 1f)) { array.asList() }
	}

	@Test
	fun colorsToDirectBufferWithAlpha() {
		val buffer = listOf(Color.RED, Color.YELLOW, Color.CYAN).toDirectBufferWithAlpha()
		assert(buffer.isDirect)
		val array = FloatArray(12)
		buffer.rewind()
		buffer.get(array)
		expect(listOf(1f, 0f, 0f, 1f, 1f, 1f, 0f, 1f, 0f, 1f, 1f, 1f)) { array.asList() }
	}
}