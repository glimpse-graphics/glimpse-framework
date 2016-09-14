package glimpse.gles

import glimpse.test.GlimpseSpec

class ViewportSpec : GlimpseSpec() {

	init {

		"Viewport" should {
			"have correct aspect ratio for 4:3 screen" {
				Viewport(1024, 768).aspect shouldBeRoughly 1.333333f
			}
			"have correct aspect ratio for 16:9 screen" {
				Viewport(1280, 720).aspect shouldBeRoughly 1.777777f
			}
			"have correct aspect ratio for 16:10 screen" {
				Viewport(320, 200).aspect shouldBe 1.6f
			}
		}

	}
}