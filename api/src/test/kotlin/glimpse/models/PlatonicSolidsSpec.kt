package glimpse.models

import glimpse.test.GlimpseSpec

class PlatonicSolidsSpec : GlimpseSpec() {

	init {

		"Tetrahedron" should {
			"have 4 faces" {
				tetrahedron().faces should haveSize(4)
			}
		}

		"Cube" should {
			"have 12 faces" {
				cube().faces should haveSize(12)
			}
		}

	}
}
