package glimpse.models

import glimpse.Vector
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class SphereSpec : GlimpseSpec() {

	init {

		"Sphere texture coordinates" should{
			"be between 0 and 1" {
				sphere(32).textureCoordinates.forEach {
					it.u should be inRange 0f..1f
					(it.u >= 0f) shouldBe true
					(it.u <= 1f) shouldBe true
					(it.v >= 0f) shouldBe true
					(it.v <= 1f) shouldBe true
				}
			}
		}

		"Sphere normals" should {
			"be normalized" {
				sphere(32).normals.forEach {
					it.magnitude shouldBeRoughly 1f
				}
			}
			"be parallel to position vectors" {
				val mesh = sphere(32)
				(mesh.positions zip mesh.normals).forEach {
					val (position, normal) = it
					normal * position.toVector() shouldBeRoughly Vector.NULL
				}
			}
			"not be opposite to position vectors" {
				val mesh = sphere(32)
				(mesh.positions zip mesh.normals).forEach {
					val (position, normal) = it
					(normal.normalize dot position.toVector() >= 0f) shouldBe true
				}
			}
		}

	}
}
