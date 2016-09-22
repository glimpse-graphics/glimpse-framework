package glimpse.cameras

import glimpse.degrees
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class OrthographicCameraProjectionBuilderSpec : GlimpseSpec() {

	init {

		"Orthographic camera projection builder" should {
			"create an instance of orthographic camera projection" {
				OrthographicCameraProjectionBuilder().build() should be a OrthographicCameraProjection::class
			}
			"create a projection with updatable height" {
				var height = 3f
				val builder = OrthographicCameraProjectionBuilder()
				builder.height { height }
				val projection = builder.build()

				projection.height() shouldBe 3f

				height = 5f
				projection.height() shouldBe 5f
			}
			"create a projection with updatable aspect ratio" {
				var aspect = 1f
				val builder = OrthographicCameraProjectionBuilder()
				builder.aspect { aspect }
				val projection = builder.build()

				projection.aspect() shouldBe 1f

				aspect = 1.5f
				projection.aspect() shouldBe 1.5f
			}
			"create a projection with given clipping planes" {
				val builder = OrthographicCameraProjectionBuilder()
				builder.distanceRange(10f to 20f)
				val projection = builder.build()
				projection.near shouldBe 10f
				projection.far shouldBe 20f

			}
		}

		"Functional orthographic camera projection builder" should {
			"create an instance of orthographic camera projection" {
				camera {
					targeted {}
					orthographic {}
				}.projection should be a OrthographicCameraProjection::class
			}
		}

	}
}