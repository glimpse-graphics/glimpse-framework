package glimpse.io

import glimpse.test.GlimpseSpec

class PropertiesSpec : GlimpseSpec() {

	init {

		"Properties" should {
			"contain first property" {
				properties("sample.properties")["FIRST_PROPERTY"] shouldEqual "first"
			}
			"contain second property" {
				properties("sample.properties")["SECOND_PROPERTY"] shouldEqual "second"
			}
			"not contain commented property" {
				properties("sample.properties")["COMMENTED_PROPERTY"] shouldBe null
			}
		}

	}
}
