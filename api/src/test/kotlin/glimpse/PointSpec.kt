package glimpse

import glimpse.test.GlimpseSpec

class PointSpec : GlimpseSpec() {

	init {

		"String representation of a point" should {
			"be properly formatted" {
				Point(123f, 456f, 789f).toString() shouldBe "(123.0, 456.0, 789.0)"
			}
		}

		"Translated point" should {
			"have correct coordinates" {
				forAll(points, vectors) { p, v ->
					p translateBy v translateBy -v == p
				}
			}
		}

		"Vector created from 2 points" should {
			"have correct coordinates" {
				forAll(points) { p ->
					(Point.ORIGIN to p) == p.toVector()
				}
			}
		}

		"Direct buffer of points" should {
			val buffer = listOf(Point(1f, 2f, 3f), Point(4f, 5f, 6f), Point(7f, 8f, 9f)).toDirectBuffer()
			"be direct" {
				buffer.isDirect
			}
			"contain a number of elements equal to the number of points times 3" {
				buffer.capacity() == 9
			}
			"contain subsequent X, Y, Z points coordinates" {
				val array = FloatArray(9)
				buffer.rewind()
				buffer.get(array)
				array.asList() shouldBe listOf(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f)
			}
		}

		"Direct buffer of augmented points" should {
			val buffer = listOf(Point(1f, 2f, 3f), Point(4f, 5f, 6f), Point(7f, 8f, 9f)).toDirectBufferAugmented()
			"be direct" {
				buffer.isDirect
			}
			"contain a number of elements equal to the number of points times 4" {
				buffer.capacity() == 12
			}
			"contain subsequent X, Y, Z, 1 points coordinates" {
				val array = FloatArray(12)
				buffer.rewind()
				buffer.get(array)
				array.asList() shouldBe listOf(1f, 2f, 3f, 1f, 4f, 5f, 6f, 1f, 7f, 8f, 9f, 1f)
			}
		}

	}
}
