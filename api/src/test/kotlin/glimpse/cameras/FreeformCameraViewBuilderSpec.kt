package glimpse.cameras

import glimpse.Angle
import glimpse.Point
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class FreeformCameraViewBuilderSpec : GlimpseSpec() {

	init {

		"Free-form camera view builder" should {
			"create an instance of free form camera view" {
				FreeformCameraViewBuilder().build() should be a FreeformCameraView::class
			}
			"create a view with updatable position" {
				var position = Point(1f, 2f, 3f)
				val builder = FreeformCameraViewBuilder()
				builder.position { position }
				val view = builder.build()

				view.position shouldBe Point(1f, 2f, 3f)

				position = Point(4f, 5f, 6f)
				view.position shouldBe Point(4f, 5f, 6f)
			}
			"create a view with updatable roll angle" {
				var roll = Angle.RIGHT
				val builder = FreeformCameraViewBuilder()
				builder.roll { roll }
				val view = builder.build()

				view.roll() shouldBe Angle.RIGHT

				roll = Angle.STRAIGHT
				view.roll() shouldBe Angle.STRAIGHT
			}
			"create a view with updatable pitch angle" {
				var pitch = Angle.NULL
				val builder = FreeformCameraViewBuilder()
				builder.pitch { pitch }
				val view = builder.build()

				view.pitch() shouldBe Angle.NULL

				pitch = -Angle.RIGHT
				view.pitch() shouldBe -Angle.RIGHT
			}
			"create a view with updatable yaw angle" {
				var yaw = Angle.STRAIGHT
				val builder = FreeformCameraViewBuilder()
				builder.yaw { yaw }
				val view = builder.build()

				view.yaw() shouldBe Angle.STRAIGHT

				yaw = Angle.NULL
				view.yaw() shouldBe Angle.NULL
			}
		}

		"Functional free-form camera view builder" should {
			"create an instance of free-form camera view" {
				camera {
					freeform {}
					perspective {}
				}.view should be a FreeformCameraView::class
			}
		}

	}
}