package glimpse.api

import glimpse.test.GlimpseSpec

class AffineTransformationSpec : GlimpseSpec() {

	init {

		"Translation matrix" should {
			"give results consistent with sum of vectors" {
				forAll(vectors, vectors) { vector, translation ->
					translationMatrix(translation) * vector isRoughly vector + translation
				}
			}
			"give results consistent with translation of a point" {
				forAll(points, vectors) { point, translation ->
					translationMatrix(translation) * point.toVector() isRoughly (point translateBy translation).toVector()
				}
			}
		}

		"Rotation" should {
			"preserve vector magnitude" {
				forAll(vectors, vectors, angles) { vector, axis, angle ->
					(rotationMatrix(axis, angle) * vector).magnitude isRoughly vector.magnitude
				}
			}
			"leave the distance between any two points unchanged" {
				forAll(points, points, vectors, angles) { a, b, axis, angle ->
					val matrix = rotationMatrix(axis, angle)
					((matrix * a) to (matrix * b)).magnitude isRoughly (a to b).magnitude
				}
			}
			"always change vector direction in the same way" {
				forAll(points, points, vectors, angles) { a, b, axis, angle ->
					val matrix = rotationMatrix(axis, angle)
					(matrix * a) to (matrix * b) isRoughly matrix * (a to b)
				}
			}
		}

		"Transformation matrix for rotation around X axis" should {
			"be consistent with rotation matrix around a unit vector in the direction of the X axis" {
				forAll(angles) { angle ->
					rotationMatrixX(angle) isRoughly rotationMatrix(Vector.X_UNIT, angle)
				}
			}
		}

		"Transformation matrix for rotation around Y axis" should {
			"be consistent with rotation matrix around a unit vector in the direction of the Y axis" {
				forAll(angles) { angle ->
					rotationMatrixY(angle) isRoughly rotationMatrix(Vector.Y_UNIT, angle)
				}
			}
		}

		"Transformation matrix for rotation around Z axis" should {
			"be consistent with rotation matrix around a unit vector in the direction of the Z axis" {
				forAll(angles) { angle ->
					rotationMatrixZ(angle) isRoughly rotationMatrix(Vector.Z_UNIT, angle)
				}
			}
		}

		"Uniform scaling" should {
			"be consistent with multiplying a vector by a scalar" {
				forAll(vectors, bigFloats) { vector, scale ->
					scalingMatrix(scale) * vector isRoughly vector * scale
				}
			}
			"increase each vector magnitude by a factor equal to absolute value of the scale" {
				forAll(vectors, bigFloats) { vector, scale ->
					(scalingMatrix(scale) * vector).magnitude isRoughly Math.abs(vector.magnitude * scale)
				}
			}
			"increase the distance between any two points by a factor equal to absolute value of the scale" {
				forAll(points, points, bigFloats) { a, b, scale ->
					val matrix = scalingMatrix(scale)
					((matrix * a) to (matrix * b)).magnitude isRoughly Math.abs((a to b).magnitude * scale)
				}
			}
		}

		"Directional scaling" should {
			"change a unit vector in the direction of the same axis" {
				forAll(bigFloats) { scale ->
					scalingMatrix(scale, 1f, 1f) * Vector.X_UNIT isRoughly Vector.X_UNIT * scale
				}
				forAll(bigFloats) { scale ->
					scalingMatrix(1f, scale, 1f) * Vector.Y_UNIT isRoughly Vector.Y_UNIT * scale
				}
				forAll(bigFloats) { scale ->
					scalingMatrix(1f, 1f, scale) * Vector.Z_UNIT isRoughly Vector.Z_UNIT * scale
				}
			}
			"not change a vector perpendicular to the direction of stretching" {
				forAll(vectors, bigFloats) { vector, scale ->
					val perpendicularVector = Vector.X_UNIT * vector
					scalingMatrix(scale, 1f, 1f) * perpendicularVector isRoughly perpendicularVector
				}
				forAll(vectors, bigFloats) { vector, scale ->
					val perpendicularVector = Vector.Y_UNIT * vector
					scalingMatrix(1f, scale, 1f) * perpendicularVector isRoughly perpendicularVector
				}
				forAll(vectors, bigFloats) { vector, scale ->
					val perpendicularVector = Vector.Z_UNIT * vector
					scalingMatrix(1f, 1f, scale) * perpendicularVector isRoughly perpendicularVector
				}
			}
		}

		"Reflection" should {
			"leave the distance between any two points unchanged" {
				forAll(points, points, vectors, points) { a, b, normal, point ->
					val matrix = reflectionMatrix(normal, point)
					((matrix * a) to (matrix * b)).magnitude isRoughly (a to b).magnitude
				}
			}
			"flip normal vector if the origin is on the reflection plane" {
				forAll(vectors) { normal ->
					reflectionMatrix(normal, Point.ORIGIN) * normal isRoughly -normal
				}
			}
			"not change a point on the reflection plane" {
				forAll(vectors, points) { normal, point ->
					reflectionMatrix(normal, point) * point isRoughly point
				}
			}
			"flip bound normal vector starting on the reflection plane" {
				forAll(vectors, points) { normal, point ->
					reflectionMatrix(normal, point) * (point translateBy normal) isRoughly (point translateBy -normal)
				}
			}
		}

	}
}
