package glimpse.lights

import glimpse.Color
import glimpse.Vector
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class PointLightBuilderSpec : GlimpseSpec() {

	init {

		"Point light builder" should {
			"create an instance of point light" {
				LightBuilder.PointLightBuilder().build() should be a Light.PointLight::class
			}
			"create a light with updatable position" {
				var position = Vector.X_UNIT.toPoint()
				val builder = LightBuilder.PointLightBuilder()
				builder.position { position }
				val light = builder.build()

				light.position().toVector() shouldBe Vector.X_UNIT

				position = Vector.Y_UNIT.toPoint()
				light.position().toVector() shouldBe Vector.Y_UNIT
			}
			"create a light with updatable distance" {
				var distance = 10f
				val builder = LightBuilder.PointLightBuilder()
				builder.distance { distance }
				val light = builder.build()

				light.distance() shouldBe 10f

				distance = 30f
				light.distance() shouldBe 30f
			}
			"create a light with updatable color" {
				var color = Color.RED
				val builder = LightBuilder.PointLightBuilder()
				builder.color { color }
				val light = builder.build()

				light.color() shouldBe Color.RED

				color = Color.CYAN
				light.color() shouldBe Color.CYAN
			}
		}

		"Functional point light builder" should {
			"create an instance of point light" {
				pointLight {} should be a Light.PointLight::class
			}
		}

	}

}