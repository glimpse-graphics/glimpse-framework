package glimpse

import glimpse.buffers.toList
import glimpse.test.GlimpseSpec

class VectorSpec : GlimpseSpec() {

	init {

		"String representation of a vector" should {
			"be properly formatted" {
				Vector(123f, 456f, 789f).toString() shouldBe "[123.0, 456.0, 789.0]"
			}
		}

		"Spherical coordinates" should {
			"translate to proper cartesian coordinates" {
				Vector(1f, Angle.Companion.NULL, Angle.Companion.NULL) shouldBeRoughly Vector(0f, 0f, 1f)
				Vector(2f, Angle.Companion.RIGHT, Angle.Companion.NULL) shouldBeRoughly Vector(2f, 0f, 0f)
				Vector(3f, Angle.Companion.RIGHT, Angle.Companion.RIGHT) shouldBeRoughly Vector(0f, 3f, 0f)
			}
			"be correctly calculated for a vector" {
				Vector(1f, 0f, 0f).magnitude shouldBeRoughly 1f
				Vector(1f, 0f, 0f).inclination shouldBeRoughly Angle.RIGHT
				Vector(1f, 0f, 0f).azimuth shouldBeRoughly Angle.NULL
			}
		}

		"Magnitude of the vector" should {
			"Be 1 for unit vectors" {
				Vector.X_UNIT.magnitude shouldBe 1f
				Vector.Y_UNIT.magnitude shouldBe 1f
				Vector.Z_UNIT.magnitude shouldBe 1f
			}
			"Be 0 for null vector" {
				Vector.NULL.magnitude shouldBe 0f
			}
		}
		"Opposite vectors" should {
			"have correct values" {
				forAll(vectors) { v ->
					-v isRoughly Vector(-(v.x), -(v.y), -(v.z))
				}
			}
		}

		"Addition of vectors" should {
			"give correct results" {
				forAll(vectors, vectors) { v1, v2 ->
					v1.x + v2.x isRoughly (v1 + v2).x
				}
				forAll(vectors, vectors) { v1, v2 ->
					v1.y + v2.y isRoughly (v1 + v2).y
				}
				forAll(vectors, vectors) { v1, v2 ->
					v1.z + v2.z isRoughly (v1 + v2).z
				}
			}
			"be equivalent to point translation" {
				forAll(vectors, vectors) { v1, v2 ->
					(v1 + v2).toPoint() isRoughly (v1.toPoint() translateBy v2)
				}
			}
		}

		"Subtraction of vectors" should {
			"give correct results" {
				forAll(vectors, vectors) { v1, v2 ->
					v1.x - v2.x isRoughly (v1 - v2).x
				}
				forAll(vectors, vectors) { v1, v2 ->
					v1.y - v2.y isRoughly (v1 - v2).y
				}
				forAll(vectors, vectors) { v1, v2 ->
					v1.z - v2.z isRoughly (v1 - v2).z
				}
			}
		}

		"Cross products of two vectors" should {
			"be null for parallel vectors" {
				forAll(floats, floats, angles, angles) { x, y, inclination, azimuth ->
					Vector(x, inclination, azimuth) * Vector(y, inclination, azimuth) isRoughly Vector.NULL
				}
			}
			"have a magnitude equal to product of the magnitudes of two orthogonal vectors" {
				forAll(floats, floats, angles, angles) { x, y, inclination, azimuth ->
					(Vector(x, inclination, azimuth) * Vector(y, inclination + Angle.Companion.RIGHT, azimuth)).magnitude isRoughly (x * y)
				}
			}
			"be anticommutative" {
				forAll(vectors, vectors) { a, b ->
					a * b isRoughly -(b * a)
				}
			}
			"be distributive over addition" {
				forAll(vectors, vectors, vectors) { a, b, c ->
					a * (b + c) isRoughly (a * b) + (a * c)
				}
			}
			"satisfy the Jacobi identity" {
				forAll(vectors, vectors, vectors) { a, b, c ->
					a * (b * c) + b * (c * a) + c * (a * b) isRoughly Vector.NULL
				}
			}
		}

		"Dot products of two vectors" should {
			"be equal to product of the magnitudes of two parallel vectors" {
				forAll(floats, floats, angles, angles) { x, y, inclination, azimuth ->
					Vector(x, inclination, azimuth) dot Vector(y, inclination, azimuth) isRoughly x * y
				}
			}
			"be 0 for orthogonal vectors" {
				forAll(floats, floats, angles, angles) { x, y, inclination, azimuth ->
					Vector(x, inclination, azimuth) dot Vector(y, inclination + Angle.Companion.RIGHT, azimuth) isRoughly 0f
				}
			}
			"be commutative" {
				forAll(vectors, vectors) { a, b ->
					(a dot b) isRoughly (b dot a)
				}
			}
			"be distributive over addition" {
				forAll(vectors, vectors, vectors) { a, b, c ->
					a dot (b + c) isRoughly (a dot b) + (a dot c)
				}
			}
		}

		"Dot and cross product" should {
			"satisfy Lagrange's formula" {
				forAll(vectors, vectors, vectors) { a, b, c ->
					a * (b * c) isRoughly (b * (a dot c) - c * (a dot b))
				}
			}
			"satisfy scalar triple product formula" {
				forAll(vectors, vectors, vectors) { a, b, c ->
					a dot (b * c) isRoughly (b dot (c * a))
				}
			}
		}

		"A vector" should {
			"be correctly multiplied by a number" {
				forAll(vectors, floats) { v, n ->
					v.x * n isRoughly (v * n).x
				}
				forAll(vectors, floats) { v, n ->
					v.y * n isRoughly (v * n).y
				}
				forAll(vectors, floats) { v, n ->
					v.z * n isRoughly (v * n).z
				}
			}
			"be correctly divided by a number" {
				forAll(vectors, positiveFloats) { v, n ->
					v.x / n isRoughly (v / n).x
				}
				forAll(vectors, positiveFloats) { v, n ->
					v.y / n isRoughly (v / n).y
				}
				forAll(vectors, positiveFloats) { v, n ->
					v.z / n isRoughly (v / n).z
				}
			}
		}

		"Vectors" should {
			"be parallel if they differ only by magnitude" {
				forAll(vectors, bigFloats) { v, x ->
					v * x parallelTo v
				}
			}
			"not be parallel if they differ by angle" {
				forAll(floats, floats, angles, angles) { x, y, inclination, azimuth ->
					!(Vector(x, inclination, azimuth) parallelTo Vector(y, inclination + Angle.Companion.RIGHT, azimuth))
				}
			}
		}

		"Normalized vector" should {
			"have correct magnitude" {
				forAll(vectors, bigFloats) { v, x ->
					v.magnitude > 0f || v.normalize(x).magnitude isRoughly x
				}
				forAll(vectors, bigFloats) { v, x ->
					v.magnitude > 0f || v.normalize.magnitude isRoughly 1f
				}
			}
		}

		"Normalizing a null vector" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					Vector.NULL.normalize
				}
			}
		}

		"Direct buffer of vectors" should {
			val buffer = listOf(Vector(1f, 2f, 3f), Vector(4f, 5f, 6f), Vector(7f, 8f, 9f)).toDirectBuffer()
			"be direct" {
				buffer.isDirect shouldBe true
			}
			"contain a number of elements equal to the number of vectors times 3" {
				buffer.capacity() shouldBe 9
			}
			"contain subsequent X, Y, Z vectors coordinates" {
				buffer.toList() shouldBe listOf(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f)
			}
		}

		"Direct buffer of augmented vectors" should {
			val buffer = listOf(Vector(1f, 2f, 3f), Vector(4f, 5f, 6f), Vector(7f, 8f, 9f)).toDirectBufferAugmented()
			"be direct" {
				buffer.isDirect shouldBe true
			}
			"contain a number of elements equal to the number of vectors times 4" {
				buffer.capacity() shouldBe 12
			}
			"contain subsequent X, Y, Z, 1 vectors coordinates" {
				buffer.toList() shouldBe listOf(1f, 2f, 3f, 1f, 4f, 5f, 6f, 1f, 7f, 8f, 9f, 1f)
			}
		}

	}
}
