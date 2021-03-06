package glimpse

import glimpse.buffers.toList
import glimpse.test.GlimpseSpec

class TextureCoordinatesSpec : GlimpseSpec() {

	init {

		"String representation of texture coordinates" should {
			"be properly formatted" {
				TextureCoordinates(0.3f, 0.7f).toString() shouldBe "(0.3, 0.7)"
			}
		}

		"Direct buffer of texture coordinates" should {
			val buffer = listOf(TextureCoordinates(0f, 1f), TextureCoordinates(1f, 1f), TextureCoordinates(1f, 0f)).toDirectBuffer()
			"be direct" {
				buffer.isDirect shouldBe true
			}
			"contain a number of elements equal to the number of texture coordinates times 2" {
				buffer.capacity() shouldBe 6
			}
			"contain subsequent UV coordinates" {
				buffer.toList() shouldBe listOf(0f, 1f, 1f, 1f, 1f, 0f)
			}
		}

	}
}
