package glimpse

import glimpse.test.GlimpseSpec
import glimpse.test.chooseFloat
import io.kotlintest.properties.Gen

class ProjectionMatrixSpec : GlimpseSpec() {

	init {

		"Perspective projection" should {
			"be consistent with frustum projection" {
				perspectiveProjectionMatrix(90.degrees, 1f, 1f, 2f) shouldBeRoughly
						frustumProjectionMatrix(-1f, 1f, -1f, 1f, 1f, 2f)
			}
			"transform all points on Z axis into visible space" {
				forAll(Gen.chooseFloat(-99, -2)) { z ->
					Point(0f, 0f, z) isVisibleIn perspectiveProjectionMatrix(90.degrees, 1f, 1f, 100f)
				}
			}
			"transform all points inside a square of size 2 times Z coordinate into visible space" {
				val z = 50
				val positions = Gen.chooseFloat(-z + 1, z - 1)
				forAll(positions, positions) { x, y ->
					Point(x, y, -z.toFloat()) isVisibleIn perspectiveProjectionMatrix(90.degrees, 1f, 1f, 100f)
				}
			}
			"transform all points outside a square of size 2 times Z coordinate into invisible space" {
				val z = 50
				val positions = Gen.chooseFloat(z + 1, z + 10)
				forAll(positions, positions) { x, y ->
					Point(x, y, -z.toFloat()) isNotVisibleIn perspectiveProjectionMatrix(90.degrees, 1f, 1f, 100f)
				}
			}
		}

		"Orthographic projection" should {
			"be consistent with identity matrix" {
				orthographicProjectionMatrix(-1f, 1f, -1f, 1f, 1f, -1f) shouldBeRoughly Matrix.IDENTITY
			}
		}

	}
}