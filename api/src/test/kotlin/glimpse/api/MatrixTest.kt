package glimpse.api

import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.expect

class MatrixTest {

	@Test
	fun matrixToString() {
		val m = Matrix((1..16).map { it.toFloat() })
		expect(
				"\n|     1.00     5.00     9.00    13.00 |" +
				"\n|     2.00     6.00    10.00    14.00 |" +
				"\n|     3.00     7.00    11.00    15.00 |" +
				"\n|     4.00     8.00    12.00    16.00 |\n"
		) {
			m.toString()
		}
	}

	@Test
	fun multiplyMatrices() {
		val m1 = Matrix((1..16).map { it.toFloat() })
		val m2 = Matrix((21..36).map { it.toFloat() })
		expect(Matrix(listOf(
				650.0f, 740.0f, 830.0f, 920.0f,
				762.0f, 868.0f, 974.0f, 1080.0f,
				874.0f, 996.0f, 1118.0f, 1240.0f,
				986.0f, 1124.0f, 1262.0f, 1400.0f))
		) { m1 * m2 }
	}

	@Test
	fun multiplyIdentityMatrix() {
		val m = Matrix((1..16).map { it.toFloat() })
		expect(m) { Matrix.IDENTITY * m }
	}

	@Test
	fun multiplyByIdentityMatrix() {
		val m = Matrix((1..16).map { it.toFloat() })
		expect(m) { m * Matrix.IDENTITY }
	}

	@Test
	fun transposeMatrix() {
		val m = Matrix((1..16).map { it.toFloat() })
		expect(Matrix(listOf(
				1.0f, 5.0f, 9.0f, 13.0f,
				2.0f, 6.0f, 10.0f, 14.0f,
				3.0f, 7.0f, 11.0f, 15.0f,
				4.0f, 8.0f, 12.0f, 16.0f
		))) { m.transpose() }
	}

	@Test
	fun invertMatrix() {
		val m = Matrix(listOf(
				4.0f, 9.0f, 5.0f, 6.0f,
				2.0f, 7.0f, 8.0f, 10.0f,
				11.0f, 12.0f, 13.0f, 14.0f,
				15.0f, 16.0f, 17.0f, 18.0f))
		println(m.invert())
		assertMatrix(Matrix.IDENTITY, 0.000001f) {
			m.invert() * m
		}
	}

	private fun assertMatrix(expected: Matrix, maxSquaredError: Float, actual: () -> Matrix) {
		assertTrue {
			maxSquaredError > expected._16f
					.zip(actual()._16f)
					.map { it.first - it.second }
					.map { it * it }
					.reduce { sum, next -> sum + next }
		}
	}
}
