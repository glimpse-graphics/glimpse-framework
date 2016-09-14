package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector
import glimpse.test.GlimpseSpec

class PrismSpec : GlimpseSpec() {

	init {

		"Prism" should {
			"have correct number of side faces" {
				prism {
					curve {
						segment(
								Vertex(Point(1f, 2f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)),
								Vertex(Point(3f, 4f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)))
					}.polygon { emptyList() }
				}.faces should haveSize(2)
			}
			"have correct number of all faces" {
				prism {
					curve {
						segment(
								Vertex(Point(1f, 2f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)),
								Vertex(Point(3f, 4f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)))
						segment(
								Vertex(Point(3f, 4f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
								Vertex(Point(5f, 6f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)))
						segment(
								Vertex(Point(5f, 6f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)),
								Vertex(Point(1f, 2f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)))
					}.polygon { listOf(Triple(0, 2, 4)) }
				}.faces should haveSize(8)
			}
		}

	}
}