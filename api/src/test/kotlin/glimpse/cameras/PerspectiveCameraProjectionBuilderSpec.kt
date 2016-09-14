package glimpse.cameras

import glimpse.degrees
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class PerspectiveCameraProjectionBuilderSpec : GlimpseSpec() {

	init {

		"Perspective camera projection builder" should {
			"create an instance of perspective camera projection" {
				PerspectiveCameraProjectionBuilder().build() should be a PerspectiveCameraProjection::class
			}
			"create a projection with updatable field of view" {
				var fov = 30.degrees
				val builder = PerspectiveCameraProjectionBuilder()
				builder.fov { fov }
				val projection = builder.build()

				projection.fovY() shouldBe 30.degrees

				fov = 90.degrees
				projection.fovY() shouldBe 90.degrees
			}
			"create a projection with updatable aspect ratio" {
				var aspect = 1f
				val builder = PerspectiveCameraProjectionBuilder()
				builder.aspect { aspect }
				val projection = builder.build()

				projection.aspect() shouldBe 1f

				aspect = 1.5f
				projection.aspect() shouldBe 1.5f
			}
			"create a projection with given clipping planes" {
				val builder = PerspectiveCameraProjectionBuilder()
				builder.distanceRange(10f to 20f)
				val projection = builder.build()
				projection.near shouldBe 10f
				projection.far shouldBe 20f

			}
		}

		"Functional perspective camera projection builder" should {
			"create an instance of perspective camera projection" {
				camera {
					targeted {}
					perspective {}
				}.projection should be a PerspectiveCameraProjection::class
			}
		}

	}
}