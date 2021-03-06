package glimpse.test

import com.nhaarman.mockito_kotlin.KStubbing
import com.nhaarman.mockito_kotlin.mock
import glimpse.Angle
import glimpse.Matrix
import glimpse.Point
import glimpse.Vector
import glimpse.cameras.Camera
import glimpse.gles.GLES
import glimpse.gles.delegates.GLESDelegate
import io.kotlintest.matchers.BeWrapper
import io.kotlintest.properties.Gen
import io.kotlintest.specs.WordSpec

abstract class GlimpseSpec : WordSpec(), FloatMatchers {

	protected companion object {

		val delta = 0.01f

		val integers = Gen.choose(-1000, 1000)
		val positiveIntegers = Gen.choose(1, 1000)
		val negativeIntegers = Gen.choose(-1000, -1)
		val floats = Gen.float()
		val positiveFloats = Gen.chooseFloat(1, 100)
		val bigFloats = Gen.chooseFloat(-100, 100)
		val angles = Gen.angle(Gen.chooseFloat(0, 360))
		val points = Gen.point(bigFloats)
		val vectors = Gen.vector(bigFloats)
		val matrices = Gen.matrix(Gen.chooseFloat(1, 100))
	}

	protected infix fun Float.isRoughly(other: Float) =
			Math.abs(other - this) < Math.max(delta, this * delta)

	protected infix fun Float.shouldBeRoughly(other: Float) {
		this isRoughly other shouldBe true
	}

	protected infix fun Angle.isRoughly(other: Angle) =
			deg isRoughly other.deg && rad isRoughly other.rad

	protected infix fun Angle.shouldBeRoughly(other: Angle) {
		this isRoughly other shouldBe true
	}

	protected infix fun Point.isRoughly(other: Point) =
			_3f.zip(other._3f).all { it.first isRoughly it.second }

	protected infix fun Point.shouldBeRoughly(other: Point) {
		this isRoughly other shouldBe true
	}

	protected infix fun Vector.isRoughly(other: Vector) =
			_3f.zip(other._3f).all { it.first isRoughly it.second }

	protected infix fun Vector.shouldBeRoughly(other: Vector) {
		this isRoughly other shouldBe true
	}

	protected infix fun Matrix.isRoughly(other: Matrix) =
			(0..3).all { row ->
				(0..3).all { col ->
					this[row, col] isRoughly other[row, col]
				}
			}

	protected infix fun Matrix.shouldBeRoughly(other: Matrix) {
		this isRoughly other shouldBe true
	}

	protected infix fun <T : Comparable<T>> BeWrapper<T>.inRange(range: ClosedRange<T>): Boolean = range.contains(value)

	protected fun glesMock(stubbing: KStubbing<GLES>.() -> Unit): GLES {
		val glesMock: GLES = mock(stubbing)
		GLESDelegate(glesMock)
		return glesMock
	}

	protected infix fun Point.isVisibleIn(camera: Camera): Boolean =
			isVisibleIn(camera.cameraMatrix)

	protected infix fun Vector.isVisibleIn(camera: Camera): Boolean =
			isVisibleIn(camera.cameraMatrix)

	protected infix fun Point.isNotVisibleIn(camera: Camera): Boolean =
			isNotVisibleIn(camera.cameraMatrix)

	protected infix fun Vector.isNotVisibleIn(camera: Camera): Boolean =
			isNotVisibleIn(camera.cameraMatrix)

	protected infix fun Point.isVisibleIn(mvpMatrix: Matrix): Boolean =
			(mvpMatrix * this)._3f.all { it in -1f..1f}

	protected infix fun Vector.isVisibleIn(mvpMatrix: Matrix): Boolean =
			(mvpMatrix * this)._3f.all { it in -1f..1f}

	protected infix fun Point.isNotVisibleIn(mvpMatrix: Matrix): Boolean =
			(mvpMatrix * this)._3f.any { it !in -1f..1f}

	protected infix fun Vector.isNotVisibleIn(mvpMatrix: Matrix): Boolean =
			(mvpMatrix * this)._3f.any { it !in -1f..1f}
}
