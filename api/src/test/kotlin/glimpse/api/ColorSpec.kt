package glimpse.api

import glimpse.test.GlimpseSpec

class ColorSpec : GlimpseSpec() {

	init {

		"String representation of a color" should {
			"be a hexadecimal ARGB value" {
				Color.BLACK.toString() shouldBe "#ff000000"
				Color.GRAY.toString() shouldBe "#ff7f7f7f"
				Color.WHITE.toString() shouldBe "#ffffffff"
				Color.RED.toString() shouldBe "#ffff0000"
				Color.GREEN.toString() shouldBe "#ff00ff00"
				Color.BLUE.toString() shouldBe "#ff0000ff"
				(Color.BLACK transparent 0.5f).toString() shouldBe "#7f000000"
			}
		}

		"Direct buffer of colors" should {
			val buffer = listOf(Color.GREEN, Color.BLUE, Color.MAGENTA).toDirectBuffer()
			"be direct" {
				buffer.isDirect
			}
			"contain a number of elements equal to the number of colors times 3" {
				buffer.capacity() == 9
			}
			"contain subsequent RGB color channels" {
				val array = FloatArray(9)
				buffer.rewind()
				buffer.get(array)
				array.asList() shouldBe listOf(0f, 1f, 0f, 0f, 0f, 1f, 1f, 0f, 1f)
			}
		}

		"Direct buffer of colors with alpha channel" should {
			val buffer = listOf(Color.RED, Color.YELLOW, Color.CYAN).toDirectBufferWithAlpha()
			"be direct" {
				buffer.isDirect
			}
			"contain a number of elements equal to the number of colors times 4" {
				buffer.capacity() == 12
			}
			"contain subsequent RGBA color channels" {
				val array = FloatArray(12)
				buffer.rewind()
				buffer.get(array)
				array.asList() shouldBe listOf(1f, 0f, 0f, 1f, 1f, 1f, 0f, 1f, 0f, 1f, 1f, 1f)
			}
		}

	}
}
