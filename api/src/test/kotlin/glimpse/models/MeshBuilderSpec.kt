package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector
import glimpse.test.GlimpseSpec

class MeshBuilderSpec : GlimpseSpec() {

	init {

		"Empty mesh" should {
			"have no positions" {
				mesh { }.positions should beEmpty()
			}
			"have no texture coordinates" {
				mesh { }.textureCoordinates should beEmpty()
			}
			"have no normals" {
				mesh { }.normals should beEmpty()
			}
			"have no vertices" {
				mesh { }.vertices should beEmpty()
			}
			"have no faces" {
				mesh { }.faces should beEmpty()
			}
		}

		"A single triangle" should {
			val triangle = mesh {
				triangle(
						Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)),
						Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
						Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)))
			}
			"have 3 positions" {
				triangle.positions should haveSize(3)
			}
			"have 3 texture coordinates" {
				triangle.textureCoordinates should haveSize(3)
			}
			"have 3 normals" {
				triangle.normals should haveSize(3)
			}
			"have 3 vertices" {
				triangle.vertices should haveSize(3)
			}
			"have 1 face" {
				triangle.faces should haveSize(1)
			}
		}

		"A single quadrilateral" should {
			val quad = mesh {
				quad(
						Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)),
						Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
						Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)),
						Vertex(Point(10f, 11f, 12f), TextureCoordinates(7f, 8f), Vector(10f, 11f, 12f)))
			}
			"have 6 positions" {
				quad.positions should haveSize(6)
			}
			"have 6 texture coordinates" {
				quad.textureCoordinates should haveSize(6)
			}
			"have 6 normals" {
				quad.normals should haveSize(6)
			}
			"have 6 vertices" {
				quad.vertices should haveSize(6)
			}
			"have 2 faces" {
				quad.faces should haveSize(2)
			}
			"consist of correct faces" {
				quad.faces shouldBe listOf(
						Face(
								Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)),
								Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
								Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f))),
						Face(
								Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)),
								Vertex(Point(10f, 11f, 12f), TextureCoordinates(7f, 8f), Vector(10f, 11f, 12f)),
								Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f))))
			}
		}

	}
}
