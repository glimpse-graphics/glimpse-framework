package glimpse.models

import glimpse.Angle
import glimpse.Vector
import glimpse.test.GlimpseSpec
import glimpse.translationMatrix
import io.kotlintest.properties.Gen

class MeshTransformationSpec : GlimpseSpec() {

	init {

		"Mesh translation" should {
			"give results consistent with sum of vectors" {
				forAll(vectors, vectors) { vector, translation ->
					mesh { }.transform {
						translate(translation)
					}.transformation() * vector isRoughly vector + translation
				}
			}
			"change in time" {
				var translation = Vector.X_UNIT
				val model = mesh { }.transform {
					translate(translation)
				}
				model.transformation() * Vector.NULL shouldBeRoughly Vector.X_UNIT
				translation = Vector.Z_UNIT
				model.transformation() * Vector.NULL shouldBeRoughly Vector.Z_UNIT
			}
		}

		"Mesh translation matrix" should {
			"give results consistent with sum of vectors" {
				forAll(vectors, vectors) { vector, translation ->
					mesh { }.transform(translationMatrix(translation)).transformation() * vector isRoughly vector + translation
				}
			}
		}

		"Composition of translations" should {
			"be equivalent to a translation by the sum vectors" {
				forAll(Gen.list(vectors)) { list ->
					mesh { }.transform {
						list.forEach { vector ->
							translate(vector)
						}
					}.transformation() isRoughly mesh { }.transform {
						translate(list.reduce { sum, next -> sum + next })
					}.transformation()
				}
			}
		}

		"Composition of a translation and a rotation" should {
			"first translate and then rotate" {
				mesh { }.transform {
					translate(Vector.X_UNIT)
					rotateZ(Angle.RIGHT)
				}.transformation() * Vector.X_UNIT shouldBeRoughly Vector.Y_UNIT * 2f
			}
		}

		"Composition of a rotation and a translation" should {
			"first rotate and then translate" {
				mesh { }.transform {
					rotateZ(Angle.RIGHT)
					translate(Vector.X_UNIT)
				}.transformation() * Vector.X_UNIT shouldBeRoughly Vector.Y_UNIT + Vector.X_UNIT
			}
		}

		"Rotation around X axis" should {
			"be consistent with rotation around a unit vector in the direction of the X axis" {
				forAll(angles) { angle ->
					mesh { }.transform { rotateX(angle) }.transformation() isRoughly mesh { }.transform { rotate(Vector.X_UNIT, angle) }.transformation()
				}
			}
		}

		"Rotation around Y axis" should {
			"be consistent with rotation around a unit vector in the direction of the Y axis" {
				forAll(angles) { angle ->
					mesh { }.transform { rotateY(angle) }.transformation() isRoughly mesh { }.transform { rotate(Vector.Y_UNIT, angle) }.transformation()
				}
			}
		}

		"Rotation around Z axis" should {
			"be consistent with rotation around a unit vector in the direction of the Z axis" {
				forAll(angles) { angle ->
					mesh { }.transform { rotateZ(angle) }.transformation() isRoughly mesh { }.transform { rotate(Vector.Z_UNIT, angle) }.transformation()
				}
			}
		}

		"Composition of a translation and a scaling" should {
			"first translate and then scale" {
				mesh { }.transform {
					translate(Vector.X_UNIT)
					scale(2f)
				}.transformation() * Vector.X_UNIT shouldBeRoughly Vector.X_UNIT * 4f
			}
		}

		"Composition of a scaling and a translation" should {
			"first scale and then translate" {
				mesh { }.transform {
					scale(2f)
					translate(Vector.X_UNIT)
				}.transformation() * Vector.X_UNIT shouldBeRoughly Vector.X_UNIT * 3f
			}
		}

	}
}
