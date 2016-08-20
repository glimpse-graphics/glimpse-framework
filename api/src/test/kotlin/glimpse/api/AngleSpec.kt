package glimpse.api

import io.kotlintest.properties.Gen
import io.kotlintest.specs.WordSpec
import matchers.FloatMatchers

class AngleSpec : WordSpec(), FloatMatchers {

	companion object {
		private val delta = 0.01f
		private val integers = Gen.choose(-1000, 1000)
		private val positiveIntegers = Gen.choose(1, 1000)
		private val negativeIntegers = Gen.choose(-1000, -1)
		private val floats = Gen.float()
	}

	init {

		"Angle created from degrees" should {
			val angle = 45.degrees
			"have correct measure in radians" {
				angle.rad shouldBe (0.78f plusOrMinus delta)
			}
			"have correct measure in degrees" {
				angle.deg shouldBe (45f plusOrMinus delta)
			}
		}

		"Angle created from radians" should {
			val angle = 2.radians
			"have correct measure in radians" {
				angle.rad shouldBe (2f plusOrMinus delta)
			}
			"have correct measure in degrees" {
				angle.deg shouldBe (114.59f plusOrMinus delta)
			}
		}

		"String representation of an angle" should {
			"display correct measure in degrees" {
				30.degrees.toString() shouldBe "30.0\u00B0"
			}
			"display correct measure in degrees for all values" {
				forAll(integers) { x ->
					x.degrees.toString() == x.toString() + ".0\u00B0"
				}
			}
		}

		"Opposite angles" should {
			"have correct values" {
				forAll(floats) { x ->
					-(x.degrees) isRoughly (-x).degrees
				}
			}
		}

		"Addition of angles" should {
			"give correct results" {
				forAll(floats, floats) { x, y ->
					x.degrees + y.degrees isRoughly (x + y).degrees
				}
			}
		}

		"Subtraction of angles" should {
			"give correct results" {
				forAll(floats, floats) { x, y ->
					x.degrees - y.degrees isRoughly (x - y).degrees
				}
			}
		}

		"Multiplication of an angle by a number" should {
			"give correct results" {
				forAll(floats, floats) { x, y ->
					x.degrees * y.toFloat() isRoughly (x * y).degrees
				}
			}
		}

		"An angle" should {
			"be correctly divided by another angle" {
				forAll(floats, floats) { x, y ->
					y == 0f || x.degrees / y.degrees isRoughly x / y
				}
			}
			"be correctly divided by a number" {
				forAll(floats, floats) { x, y ->
					y == 0f || x.degrees / y isRoughly (x / y).degrees
				}
			}
		}

		"Modulo of two angles" should {
			"give correct results for positive numbers" {
				forAll(floats, floats) { x, y ->
					y == 0f || x.degrees % y.degrees isRoughly (x % y).degrees
				}
				forAll(positiveIntegers, positiveIntegers) { x, y ->
					x.radians % y.radians isRoughly (x % y).radians || (x % y == 0 && x.radians % y.radians isRoughly y.radians)
				}
			}
			"give correct results for negative numbers" {
				forAll(floats, floats) { x, y ->
					y == 0f || (-x).degrees % (-y).degrees isRoughly -(x % y).degrees
				}
				forAll(negativeIntegers, negativeIntegers) { x, y ->
					x.radians % y.radians isRoughly (x % y).radians
					x.radians % y.radians isRoughly (x % y).radians || (x % y == 0 && x.radians % y.radians isRoughly y.radians)
				}
			}
		}

		"Sine of an angle" should {
			"give correct results" {
				forAll(floats) { x ->
					sin(x.radians) isRoughly Math.sin(x.toDouble()).toFloat()
				}
			}
		}

		"Cosine of an angle" should {
			"give correct results" {
				forAll(floats) { x ->
					cos(x.radians) isRoughly Math.cos(x.toDouble()).toFloat()
				}
			}
		}

		"Tangent of an angle" should {
			"give correct results" {
				forAll(floats) { x ->
					tan(x.radians) isRoughly Math.tan(x.toDouble()).toFloat()
				}
			}
		}

		"Arctangent calculated from X and Y coordinates" should {
			"give correct angle" {
				forAll(floats, floats) { x, y ->
					atan2(y, x).rad isRoughly Math.atan2(y.toDouble(), x.toDouble()).toFloat();
				}
			}
		}

		"Absolute value of an angle" should {
			"be equal to the original angle for positive values" {
				forAll(positiveIntegers) { x ->
					abs(x.radians) == x.radians
				}
			}
			"be opposite to the original angle for negative values" {
				forAll(negativeIntegers) { x ->
					abs(x.radians) == -x.radians
				}
			}
			"be equal to zero for a null angle" {
				abs(0.degrees) shouldBe Angle.NULL
			}
		}

		"Coterminal angle" should {
			"be greater or equal if counter-clockwise" {
				forAll(floats, floats) { x, y ->
					x.radians counterClockwiseFrom y.radians >= y.radians
				}
			}
			"be less or equal if clockwise" {
				forAll(floats, floats) { x, y ->
					x.radians clockwiseFrom y.radians <= y.radians
				}
			}
			"differ by a multiple of a full angle" {
				forAll(integers, integers) { x, y ->
					val modulo = abs((x.radians counterClockwiseFrom y.radians) - x.radians) % Angle.FULL
					modulo isRoughly Angle.NULL || modulo isRoughly Angle.FULL
				}
				forAll(integers, integers) { x, y ->
					val modulo = abs((x.radians clockwiseFrom y.radians) - x.radians) % Angle.FULL
					modulo isRoughly Angle.NULL || modulo isRoughly Angle.FULL
				}
			}
			"differ by less than full angle from the other angle" {
				forAll(integers, integers) { x, y ->
					abs((x.radians counterClockwiseFrom y.radians) - y.radians) < Angle.FULL + delta.degrees
				}
				forAll(integers, integers) { x, y ->
					abs((x.radians clockwiseFrom y.radians) - y.radians) < Angle.FULL + delta.degrees
				}
			}
		}

		"List of angles" should {
			"be properly sorted" {
				forAll(Gen.list(integers)) { list ->
					list.map { it.degrees }.sorted() == list.sorted().map { it.degrees }
				}
			}
		}

		"Set of angles" should {
			"not contain duplicates" {
				forAll(Gen.list(integers)) { list ->
					(list + list).map { it.degrees }.toSet().size == list.toSet().size
				}
			}
		}

		"Range of angles" should {
			"be properly partitioned" {
				Angle.NULL..Angle.STRAIGHT partition 4 shouldBe listOf(0.degrees, 45.degrees, 90.degrees, 135.degrees, 180.degrees)
			}
		}

	}

	private infix fun Float.isRoughly(other: Float) =
			Math.abs(other - this) < delta

	private infix fun Angle.isRoughly(other: Angle) =
			deg isRoughly other.deg && rad isRoughly other.rad
}
