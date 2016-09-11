package glimpse

import glimpse.test.GlimpseSpec

class ProjectionMatrixSpec : GlimpseSpec() {

	init {

		"Perspective projection" should {
			"be consistent with frustum projection" {
				println(perspectiveProjectionMatrix(90.degrees, 1f, 1f, 2f))
				println(frustumProjectionMatrix(-1f, 1f, -1f, 1f, 1f, 2f))
				perspectiveProjectionMatrix(90.degrees, 1f, 1f, 2f) shouldBeRoughly
						frustumProjectionMatrix(-1f, 1f, -1f, 1f, 1f, 2f)
			}
		}

	}
}