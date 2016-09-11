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

	}
}
