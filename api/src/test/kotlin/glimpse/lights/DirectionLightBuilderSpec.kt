package glimpse.lights

import glimpse.Color
import glimpse.Vector
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class DirectionLightBuilderSpec : GlimpseSpec() {

	init {

		"Direction light builder" should {
			"create an instance of direction light" {
				LightBuilder.DirectionLightBuilder().build() should be a Light.DirectionLight::class
			}
			"create a light with updatable direction" {
				var direction = Vector.X_UNIT
				val builder = LightBuilder.DirectionLightBuilder()
				builder.direction { direction }
				val light = builder.build()

				light.direction() shouldBe Vector.X_UNIT

				direction = Vector.Y_UNIT
				light.direction() shouldBe Vector.Y_UNIT
			}
			"create a light with updatable color" {
				var color = Color.RED
				val builder = LightBuilder.DirectionLightBuilder()
				builder.color { color }
				val light = builder.build()

				light.color() shouldBe Color.RED

				color = Color.CYAN
				light.color() shouldBe Color.CYAN
			}
		}

		"Functional direction light builder" should {
			"create an instance of direction light" {
				directionLight {} should be a Light.DirectionLight::class
			}
		}

	}

}