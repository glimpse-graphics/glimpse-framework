package glimpse.api

import org.junit.Test
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
}
