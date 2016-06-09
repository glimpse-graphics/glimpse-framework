package glimpse.api

import org.junit.Test
import kotlin.test.expect

class TextureCoordinatesTest {

	@Test
	fun colorsToDirectBuffer() {
		val buffer = listOf(TextureCoordinates(0f, 1f), TextureCoordinates(1f, 1f), TextureCoordinates(1f, 0f)).toDirectBuffer()
		assert(buffer.isDirect)
		val array = FloatArray(6)
		buffer.rewind()
		buffer.get(array)
		expect(listOf(0f, 1f, 1f, 1f, 1f, 0f)) { array.asList() }
	}
}
