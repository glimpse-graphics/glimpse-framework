package glimpse

import glimpse.test.GlimpseSpec

class ProjectionMatrixSpec : GlimpseSpec() {

	init {

		"Perspective projection" should {
			"be consistent with frustum projection" {
				perspectiveProjectionMatrix(90.degrees, 1f, 1f, 2f) shouldBeRoughly
						frustumProjectionMatrix(-1f, 1f, -1f, 1f, 1f, 2f)
			}
		}

		"Orthographic projection" should {
			"be consistent with identity matrix" {
				orthographicProjectionMatrix(-1f, 1f, -1f, 1f, 1f, -1f) shouldBeRoughly Matrix.IDENTITY
			}
		}

	}
}