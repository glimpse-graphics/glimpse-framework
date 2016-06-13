package glimpse.api

import org.junit.Test
import kotlin.test.expect

class SquareMatrixTest {

	@Test
	fun determinantIdentity3x3() {
		expect(1f) {
			SquareMatrix.identity(3).det
		}
	}

	@Test
	fun determinant3x3() {
		expect(0f) {
			SquareMatrix(3) { row, col -> (row + col + 1).toFloat() }.det
		}
	}

	@Test
	fun determinantIdentity4x4() {
		expect(1f) {
			SquareMatrix.identity(4).det
		}
	}

	@Test
	fun determinant4x4() {
		expect(24f) {
			SquareMatrix(4) { row, col -> if (row == col) (row + 1).toFloat() else 0f }.det
		}
	}

	@Test
	fun matrixAsList() {
		expect(listOf(1f, 2f, 3f, 4f, 2f, 3f, 4f, 5f, 3f, 4f, 5f, 6f, 4f, 5f, 6f, 7f)) {
			SquareMatrix(4) { row, col ->
				(row + col + 1).toFloat()
			}.asList()
		}
	}
}