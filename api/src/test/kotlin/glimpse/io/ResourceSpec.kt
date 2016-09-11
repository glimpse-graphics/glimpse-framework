package glimpse.io

import glimpse.test.GlimpseSpec

class ResourceSpec : GlimpseSpec() {

	init {

		"Resource" should {
			"be loaded to strings" {
				Resource(Resource::class.java, "lines.txt").lines shouldBe listOf("a file", "with", "a few", "lines")
			}
		}

		"Resource in context of the object" should {
			"be loaded to strings" {
				resource("lines.txt").lines shouldBe listOf("a file", "with", "a few", "lines")
			}
		}

	}
}
