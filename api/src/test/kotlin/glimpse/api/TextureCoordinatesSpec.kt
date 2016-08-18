package glimpse.api

import io.kotlintest.specs.WordSpec

class TextureCoordinatesSpec : WordSpec() {

	init {

		"Direct buffer of texture coordinates" should {
			val buffer = listOf(TextureCoordinates(0f, 1f), TextureCoordinates(1f, 1f), TextureCoordinates(1f, 0f)).toDirectBuffer()
			"be direct" {
				buffer.isDirect
			}
			"contain a number of elements equal to the number of texture coordinates times 2" {
				buffer.capacity() == 6
			}
			"contain subsequent RGB color channels" {
				val array = FloatArray(6)
				buffer.rewind()
				buffer.get(array)
				array.asList() shouldBe listOf(0f, 1f, 1f, 1f, 1f, 0f)
			}
		}

	}

}
