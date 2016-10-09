package glimpse.lights

import glimpse.Color
import glimpse.Point
import glimpse.Vector
import glimpse.degrees
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class SpotlightBuilderSpec : GlimpseSpec() {

	init {

		"Spotlight builder" should {
			"create an instance of spotlight" {
				LightBuilder.SpotlightBuilder().build() should be a Light.Spotlight::class
			}
			"create a light with updatable position" {
				var position = Vector.X_UNIT.toPoint()
				val builder = LightBuilder.SpotlightBuilder()
				builder.position { position }
				val light = builder.build()

				light.position().toVector() shouldBe Vector.X_UNIT

				position = Vector.Y_UNIT.toPoint()
				light.position().toVector() shouldBe Vector.Y_UNIT
			}
			"create a light with updatable target" {
				var target = Point.ORIGIN
				val builder = LightBuilder.SpotlightBuilder()
				builder.target { target }
				val light = builder.build()

				light.target() shouldBe Point.ORIGIN

				target = Vector.Z_UNIT.toPoint()
				light.target().toVector() shouldBe Vector.Z_UNIT
			}
			"create a light with updatable angle" {
				var angle = 60.degrees
				val builder = LightBuilder.SpotlightBuilder()
				builder.angle { angle }
				val light = builder.build()

				light.angle() shouldBe 60.degrees

				angle = 90.degrees
				light.angle() shouldBe 90.degrees
			}
			"create a light with updatable distance" {
				var distance = 10f
				val builder = LightBuilder.SpotlightBuilder()
				builder.distance { distance }
				val light = builder.build()

				light.distance() shouldBe 10f

				distance = 30f
				light.distance() shouldBe 30f
			}
			"create a light with updatable color" {
				var color = Color.RED
				val builder = LightBuilder.SpotlightBuilder()
				builder.color { color }
				val light = builder.build()

				light.color() shouldBe Color.RED

				color = Color.CYAN
				light.color() shouldBe Color.CYAN
			}
		}

		"Functional spotlight builder" should {
			"create an instance of spotlight" {
				spotlight {} should be a Light.Spotlight::class
			}
		}

	}

}