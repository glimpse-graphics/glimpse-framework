package glimpse.cameras

import glimpse.Point
import glimpse.Vector
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class TargetedCameraViewBuilderSpec : GlimpseSpec() {

	init {

		"Targeted camera view builder" should {
			"create an instance of targeted camera view" {
				TargetedCameraViewBuilder().build() should be a TargetedCameraView::class
			}
			"create a view with updatable position" {
				var position = Point(1f, 2f, 3f)
				val builder = TargetedCameraViewBuilder()
				builder.position { position }
				val view = builder.build()

				view.position shouldBe Point(1f, 2f, 3f)

				position = Point(4f, 5f, 6f)
				view.position shouldBe Point(4f, 5f, 6f)
			}
			"create a view with updatable target" {
				var target = Point(1f, 2f, 3f)
				val builder = TargetedCameraViewBuilder()
				builder.target { target }
				val view = builder.build()

				view.target() shouldBe Point(1f, 2f, 3f)

				target = Point(4f, 5f, 6f)
				view.target() shouldBe Point(4f, 5f, 6f)
			}
			"create a view with updatable up vector" {
				var up = Vector.Z_UNIT
				val builder = TargetedCameraViewBuilder()
				builder.up { up }
				val view = builder.build()

				view.up() shouldBe Vector.Z_UNIT

				up = Vector.X_UNIT
				view.up() shouldBe Vector.X_UNIT
			}
		}

		"Functional targeted camera view builder" should {
			"create an instance of targeted camera view" {
				camera {
					targeted {}
					perspective {}
				}.view should be a TargetedCameraView::class
			}
		}

	}
}