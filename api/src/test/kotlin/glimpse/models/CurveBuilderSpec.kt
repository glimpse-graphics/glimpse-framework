package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector
import glimpse.test.GlimpseSpec

class CurveBuilderSpec : GlimpseSpec() {

	init {

		"Empty curve" should {
			"have no positions" {
				curve { }.positions should beEmpty()
			}
			"have no texture coordinates" {
				curve { }.textureCoordinates should beEmpty()
			}
			"have no normals" {
				curve { }.normals should beEmpty()
			}
			"have no vertices" {
				curve { }.vertices should beEmpty()
			}
			"have no segments" {
				curve { }.segments should beEmpty()
			}
		}

		"A single segment" should {
			val segment = curve {
				segment(Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)) to
						Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)))
			}
			"have 2 positions" {
				segment.positions should haveSize(2)
			}
			"have 2 texture coordinates" {
				segment.textureCoordinates should haveSize(2)
			}
			"have 2 normals" {
				segment.normals should haveSize(2)
			}
			"have 2 vertices" {
				segment.vertices should haveSize(2)
			}
			"have 1 segment" {
				segment.segments should haveSize(1)
			}
		}

	}
}
