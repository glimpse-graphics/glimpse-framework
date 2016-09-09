package glimpse.models

import glimpse.Angle
import glimpse.Matrix
import glimpse.Vector
import glimpse.test.GlimpseSpec
import glimpse.translationMatrix

class ModelTransformationSpec : GlimpseSpec() {

	init {

		"Model translation" should {
			"give results consistent with sum of vectors" {
				forAll(vectors, vectors) { vector, translation ->
					Model(mesh { }, Matrix.IDENTITY).transform {
						translate(translation)
					}.modelMatrix * vector isRoughly vector + translation
				}
				forAll(vectors, vectors) { vector, translation ->
					Model(mesh { }, Matrix.IDENTITY).transform(translationMatrix(translation)).modelMatrix * vector isRoughly vector + translation
				}
			}
		}

		"Composition of a translation and a rotation" should {
			"first translate and then rotate" {
				Model(mesh { }, Matrix.IDENTITY).transform {
					translate(Vector.X_UNIT)
					rotateZ(Angle.RIGHT)
				}.modelMatrix * Vector.X_UNIT shouldBeRoughly Vector.Y_UNIT * 2f
			}
		}

		"Composition of a rotation and a translation" should {
			"first rotate and then translate" {
				Model(mesh { }, Matrix.IDENTITY).transform {
					rotateZ(Angle.RIGHT)
					translate(Vector.X_UNIT)
				}.modelMatrix * Vector.X_UNIT shouldBeRoughly Vector.Y_UNIT + Vector.X_UNIT
			}
		}

		"Composition of a translation and a scaling" should {
			"first translate and then scale" {
				Model(mesh { }, Matrix.IDENTITY).transform {
					translate(Vector.X_UNIT)
					scale(2f)
				}.modelMatrix * Vector.X_UNIT shouldBeRoughly Vector.X_UNIT * 4f
			}
		}

		"Composition of a scaling and a translation" should {
			"first scale and then translate" {
				Model(mesh { }, Matrix.IDENTITY).transform {
					scale(2f)
					translate(Vector.X_UNIT)
				}.modelMatrix * Vector.X_UNIT shouldBeRoughly Vector.X_UNIT * 3f
			}
		}

	}
}
