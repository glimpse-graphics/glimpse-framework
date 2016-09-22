package glimpse.cameras

import glimpse.Point
import glimpse.Vector
import glimpse.degrees
import glimpse.test.GlimpseSpec
import glimpse.test.chooseFloat
import io.kotlintest.properties.Gen

class CameraVisibilitySpec : GlimpseSpec() {

	init {

		"Targeted perspective camera" should {
			"see the target point" {
				forAll(points) { target ->
					target isVisibleIn camera {
						perspective {
							fov { 60.degrees }
							aspect { 1.5f }
							distanceRange(1f to 1000f)
						}
						targeted {
							position { Point(-110f, -110f, -110f) }
							target { target }
							up { Vector.Z_UNIT }
						}
					}
				}
			}
			"see all points on its axis" {
				val camera = camera {
					perspective {
						fov { 60.degrees }
						aspect { 1.5f }
						distanceRange(1f to 100f)
					}
					targeted {
						position { Point(-50f, 0f, 0f) }
						target { Point.ORIGIN }
						up { Vector.Z_UNIT }
					}
				}
				forAll(Gen.chooseFloat(-48, 48)) { x ->
					Point(x, 0f, 0f) isVisibleIn camera
				}
			}
			"see all points inside the frustum" {
				val camera = camera {
					perspective {
						fov { 60.degrees }
						aspect { 1.5f }
						distanceRange(1f to 100f)
					}
					targeted {
						position { Point(-50f, 0f, 0f) }
						target { Point.ORIGIN }
						up { Vector.Z_UNIT }
					}
				}
				forAll(Gen.chooseFloat(-43, 43), Gen.chooseFloat(-28, 28)) { y, z ->
					Point(0f, y, z) isVisibleIn camera
				}
			}
			"not see any point outside the frustum" {
				val camera = camera {
					perspective {
						fov { 60.degrees }
						aspect { 1.5f }
						distanceRange(1f to 100f)
					}
					targeted {
						position { Point(-50f, 0f, 0f) }
						target { Point.ORIGIN }
						up { Vector.Z_UNIT }
					}
				}
				forAll(Gen.chooseFloat(44, 100), Gen.chooseFloat(29, 100)) { y, z ->
					Point(0f, y, z) isNotVisibleIn camera
				}
				forAll(Gen.chooseFloat(-100, -44), Gen.chooseFloat(-100, -29)) { y, z ->
					Point(0f, y, z) isNotVisibleIn camera
				}
			}
		}

		"Targeted orthographic camera" should {
			"see the target point" {
				forAll(points) { target ->
					target isVisibleIn camera {
						orthographic {
							height { 10f }
							aspect { 1.5f }
							distanceRange(1f to 1000f)
						}
						targeted {
							position { Point(-110f, -110f, -110f) }
							target { target }
							up { Vector.Z_UNIT }
						}
					}
				}
			}
			"see all points on its axis" {
				val camera = camera {
					orthographic {
						height { 10f }
						aspect { 1.5f }
						distanceRange(1f to 100f)
					}
					targeted {
						position { Point(-50f, 0f, 0f) }
						target { Point.ORIGIN }
						up { Vector.Z_UNIT }
					}
				}
				forAll(Gen.chooseFloat(-48, 48)) { x ->
					Point(x, 0f, 0f) isVisibleIn camera
				}
			}
			"see all points inside the cuboid" {
				val camera = camera {
					orthographic {
						height { 10f }
						aspect { 1.5f }
						distanceRange(1f to 100f)
					}
					targeted {
						position { Point(-50f, 0f, 0f) }
						target { Point.ORIGIN }
						up { Vector.Z_UNIT }
					}
				}
				forAll(Gen.chooseFloat(-48, 48), Gen.chooseFloat(-7, 7), Gen.chooseFloat(-4, 4)) { x, y, z ->
					Point(x, y, z) isVisibleIn camera
				}
			}
			"not see any point outside the cuboid" {
				val camera = camera {
					orthographic {
						height { 10f }
						aspect { 1.5f }
						distanceRange(1f to 100f)
					}
					targeted {
						position { Point(-50f, 0f, 0f) }
						target { Point.ORIGIN }
						up { Vector.Z_UNIT }
					}
				}
				forAll(Gen.chooseFloat(-48, 48), Gen.chooseFloat(8, 20), Gen.chooseFloat(6, 20)) { x, y, z ->
					Point(x, y, z) isNotVisibleIn camera
				}
				forAll(Gen.chooseFloat(-48, 48), Gen.chooseFloat(-20, -8), Gen.chooseFloat(-20, -6)) { x, y, z ->
					Point(x, y, z) isNotVisibleIn camera
				}
			}
		}

	}
}
