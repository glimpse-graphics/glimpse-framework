package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector
import glimpse.test.GlimpseSpec

class CurveSpec : GlimpseSpec() {

	companion object {
		val positions = listOf(
				Point(1f, 2f, 3f), Point(4f, 5f, 6f),
				Point(4f, 5f, 6f), Point(7f, 8f, 9f),
				Point(7f, 8f, 9f), Point(11f, 12f, 13f))
		val textureCoordinates = listOf(
				TextureCoordinates(1f, 2f), TextureCoordinates(3f, 4f),
				TextureCoordinates(3f, 4f), TextureCoordinates(5f, 6f),
				TextureCoordinates(5f, 6f), TextureCoordinates(11f, 12f))
		val normals = listOf(
				Vector(1f, 2f, 3f), Vector(4f, 5f, 6f),
				Vector(4f, 5f, 6f), Vector(7f, 8f, 9f),
				Vector(7f, 8f, 9f), Vector(11f, 12f, 13f))
	}

	init {

		"Initializing a curve with a list of positions of a wrong size" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					val positions = listOf(Point(1f, 2f, 3f), Point(4f, 5f, 6f), Point(7f, 8f, 9f))
					val textureCoordinates = listOf(TextureCoordinates(1f, 2f), TextureCoordinates(3f, 4f), TextureCoordinates(5f, 6f))
					val normals = listOf(Vector(1f, 2f, 3f), Vector(4f, 5f, 6f), Vector(7f, 8f, 9f))
					Curve(positions, textureCoordinates, normals)
				}
			}
		}

		"Initializing a curve with a list of texture coordinates of a wrong size" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					val textureCoordinates = listOf(TextureCoordinates(1f, 2f), TextureCoordinates(3f, 4f))
					Curve(positions, textureCoordinates, normals)
				}
			}
		}

		"Initializing a curve with a list of normals of a wrong size" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					val normals = listOf(Vector(1f, 2f, 3f), Vector(4f, 5f, 6f))
					Curve(positions, textureCoordinates, normals)
				}
			}
		}

		"Vertices of a curve" should {
			"should have a correct size" {
				Curve(positions, textureCoordinates, normals).vertices should haveSize(6)
			}
			"should have a correct values" {
				Curve(positions, textureCoordinates, normals).vertices shouldBe listOf(
						Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)),
						Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
						Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
						Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)),
						Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)),
						Vertex(Point(11f, 12f, 13f), TextureCoordinates(11f, 12f), Vector(11f, 12f, 13f)))
			}
		}

		"Segments of a curve" should {
			"should have a correct size" {
				Curve(positions, textureCoordinates, normals).segments should haveSize(3)
			}
			"should have a correct values" {
				Curve(positions, textureCoordinates, normals).segments shouldBe listOf(
						Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)) to
								Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
						Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)) to
								Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)),
						Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)) to
								Vertex(Point(11f, 12f, 13f), TextureCoordinates(11f, 12f), Vector(11f, 12f, 13f)))
			}
		}

		"Flat curve" should {
			"have the same Z coordinate for all points" {
				Curve(positions, textureCoordinates, normals).toFlatCurve(123.45f).positions.forEach {
					it.z shouldBe 123.45f
				}
			}
			"have X and Y coordinates the same as the original curve" {
				(Curve(positions, textureCoordinates, normals).toFlatCurve(123.45f).positions zip positions).forEach {
					it.first.x shouldBe it.second.x
					it.first.y shouldBe it.second.y
				}
			}
			"have normals the same as the original curve" {
				(Curve(positions, textureCoordinates, normals).toFlatCurve(123.45f).normals zip normals).forEach {
					it.first shouldBe it.second
				}
			}
			"have texture coordinates the same as the original curve" {
				(Curve(positions, textureCoordinates, normals).toFlatCurve(123.45f).textureCoordinates zip textureCoordinates).forEach {
					it.first shouldBe it.second
				}
			}
		}

		"Polygon bounded by a curve" should {
			"have this curve as its boundary" {
				val curve = Curve(positions, textureCoordinates, normals)
				curve.polygon { emptyList() }.curve shouldBe curve
			}
			"have given faces" {
				val curve = Curve(positions, textureCoordinates, normals)
				curve.polygon { listOf(Triple(0, 1, 2), Triple(2, 3, 0)) }.faces shouldBe listOf(Triple(0, 1, 2), Triple(2, 3, 0))
			}
		}

	}
}
