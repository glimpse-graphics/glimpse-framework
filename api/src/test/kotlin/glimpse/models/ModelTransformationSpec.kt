package glimpse.models

import glimpse.Angle
import glimpse.Vector
import glimpse.test.GlimpseSpec
import glimpse.translationMatrix

class ModelTransformationSpec : GlimpseSpec() {

	init {

		"Model translation" should {
			"give results consistent with sum of vectors" {
				forAll(vectors, vectors) { vector, translation ->
					Model(mesh { }).transform {
						translate(translation)
					}.transformation() * vector isRoughly vector + translation
				}
				forAll(vectors, vectors) { vector, translation ->
					Model(mesh { }).transform(translationMatrix(translation)).transformation() * vector isRoughly vector + translation
				}
			}
			"change over time" {
				var translation = Vector.X_UNIT
				val model = Model(mesh { }).transform {
					translate(translation)
				}
				model.transformation() * Vector.NULL shouldBeRoughly Vector.X_UNIT
				translation = Vector.Z_UNIT
				model.transformation() * Vector.NULL shouldBeRoughly Vector.Z_UNIT
			}
			"keep mesh transformation changes over time" {
				var translation = Vector.X_UNIT
				val model = mesh {
				}.transform {
					translate(translation)
				}.transform {
					translate(Vector.Y_UNIT)
				}
				model.transformation() * Vector.NULL shouldBeRoughly Vector(1f, 1f, 0f)
				translation = Vector.Z_UNIT
				model.transformation() * Vector.NULL shouldBeRoughly Vector(0f, 1f, 1f)
			}
		}

		"Composition of a translation and a rotation" should {
			"first translate and then rotate" {
				Model(mesh { }).transform {
					translate(Vector.X_UNIT)
					rotateZ(Angle.RIGHT)
				}.transformation() * Vector.X_UNIT shouldBeRoughly Vector.Y_UNIT * 2f
			}
		}

		"Composition of a rotation and a translation" should {
			"first rotate and then translate" {
				Model(mesh { }).transform {
					rotateZ(Angle.RIGHT)
					translate(Vector.X_UNIT)
				}.transformation() * Vector.X_UNIT shouldBeRoughly Vector.Y_UNIT + Vector.X_UNIT
			}
		}

		"Composition of a translation and a scaling" should {
			"first translate and then scale" {
				Model(mesh { }).transform {
					translate(Vector.X_UNIT)
					scale(2f)
				}.transformation() * Vector.X_UNIT shouldBeRoughly Vector.X_UNIT * 4f
			}
		}

		"Composition of a scaling and a translation" should {
			"first scale and then translate" {
				Model(mesh { }).transform {
					scale(2f)
					translate(Vector.X_UNIT)
				}.transformation() * Vector.X_UNIT shouldBeRoughly Vector.X_UNIT * 3f
			}
		}

	}
}
