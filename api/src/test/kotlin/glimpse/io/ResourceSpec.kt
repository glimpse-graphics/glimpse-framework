package glimpse.io

import glimpse.test.GlimpseSpec

class ResourceSpec : GlimpseSpec() {

	init {

		"Resource" should {
			"be loaded to strings" {
				Resource(Resource::class.java, "lines.txt").lines shouldBe listOf("a file", "with", "a few", "lines")
			}
		}

		"Non-existing resource" should {
			"cause an exception" {
				shouldThrow<ResourceNotFoundException> {
					resource("non-existing").inputStream
				}
			}
		}

		"Resource in context of the object" should {
			"be loaded to strings" {
				resource("lines.txt").lines shouldBe listOf("a file", "with", "a few", "lines")
			}
		}

	}
}
