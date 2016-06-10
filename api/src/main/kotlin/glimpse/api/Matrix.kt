package glimpse.api

/**
 * Matrix defining three-dimensional affine transformations.
 */
data class Matrix(private val matrix: List<Float>) {

	companion object {

		/**
		 * Identity matrix.
		 */
		val IDENTITY = matrix {
			val (row, col) = it
			if (row === col) 1f else 0f
		}

		/**
		 * Null matrix.
		 */
		val NULL = Matrix((0..15).map { 0f })

		private fun Int.toRowCol() = Pair(this % 4, this / 4)

		private fun matrix(transform: (Pair<Int, Int>) -> Float) =
				Matrix((0..15).map { transform(it.toRowCol()) })
	}

	init {
		require(matrix.size === 16) {
			"Matrix must consist of exactly 16 (4×4) elements. ${matrix.size} elements were provided instead."
		}
	}

	internal val _16f : Array<Float> by lazy { matrix.toTypedArray() }

	/**
	 * Returns a string representation of the [Matrix].
	 */
	override fun toString() =
			(0..3).map { row ->
				(0..3).map { col ->
					"%8.2f".format(this[row, col])
				}.joinToString(separator = " ", prefix = "| ", postfix = " |") { it }
			}.joinToString(separator = "\n", prefix = "\n", postfix = "\n") { it }

	/**
	 * Gets element of the matrix at the given position.
	 *
	 * @param row Row index.
	 * @param col Column index.
	 */
	operator fun get(row: Int, col: Int) = matrix[col * 4 + row]

	/**
	 * Multiplies this matrix by the [other] matrix.
	 */
	operator fun times(other: Matrix) = matrix {
		val (row, col) = it
		(0..3).map {this[row, it] * other[it, col] }.reduce { sum, next -> sum + next }
	}

	/**
	 * Returns a transposed matrix.
	 */
	fun transpose() = matrix {
		val (row, col) = it
		this[col, row]
	}

	/**
	 * Returns an inverted matrix.
	 */
	fun invert(): Matrix {
		val m = transpose()._16f
		val buf1 = floatArrayOf(
				m[10] * m[15], m[11] * m[14], m[9] * m[15], m[11] * m[13], m[9] * m[14], m[10] * m[13],
				m[8] * m[15], m[11] * m[12], m[8] * m[14], m[10] * m[12], m[8] * m[13], m[9] * m[12])
		val buf2 = floatArrayOf(
				m[2] * m[7], m[3] * m[6], m[1] * m[7], m[3] * m[5], m[1] * m[6], m[2] * m[5],
				m[0] * m[7], m[3] * m[4], m[0] * m[6], m[2] * m[4], m[0] * m[5], m[1] * m[4])
		val result = listOf(
				buf1[0] * m[5] + buf1[3] * m[6] + buf1[4] * m[7] - buf1[1] * m[5] - buf1[2] * m[6] - buf1[5] * m[7],
				buf1[1] * m[4] + buf1[6] * m[6] + buf1[9] * m[7] - buf1[0] * m[4] - buf1[7] * m[6] - buf1[8] * m[7],
				buf1[2] * m[4] + buf1[7] * m[5] + buf1[10] * m[7] - buf1[3] * m[4] - buf1[6] * m[5] - buf1[11] * m[7],
				buf1[5] * m[4] + buf1[8] * m[5] + buf1[11] * m[6] -  buf1[4] * m[4] - buf1[9] * m[5] - buf1[10] * m[6],
				buf1[1] * m[1] + buf1[2] * m[2] + buf1[5] * m[3] - buf1[0] * m[1] - buf1[3] * m[2] - buf1[4] * m[3],
				buf1[0] * m[0] + buf1[7] * m[2] + buf1[8] * m[3] - buf1[1] * m[0] - buf1[6] * m[2] - buf1[9] * m[3],
				buf1[3] * m[0] + buf1[6] * m[1] + buf1[11] * m[3] - buf1[2] * m[0] - buf1[7] * m[1] - buf1[10] * m[3],
				buf1[4] * m[0] + buf1[9] * m[1] + buf1[10] * m[2] - buf1[5] * m[0] - buf1[8] * m[1] - buf1[11] * m[2],
				buf2[0] * m[13] + buf2[3] * m[14] + buf2[4] * m[15] - buf2[1] * m[13] - buf2[2] * m[14] - buf2[5] * m[15],
				buf2[1] * m[12] + buf2[6] * m[14] + buf2[9] * m[15] - buf2[0] * m[12] - buf2[7] * m[14] - buf2[8] * m[15],
				buf2[2] * m[12] + buf2[7] * m[13] + buf2[10] * m[15] - buf2[3] * m[12] - buf2[6] * m[13] - buf2[11] * m[15],
				buf2[5] * m[12] + buf2[8] * m[13] + buf2[11] * m[14] - buf2[4] * m[12] - buf2[9] * m[13] - buf2[10] * m[14],
				buf2[2] * m[10] + buf2[5] * m[11] + buf2[1] * m[9] - buf2[4] * m[11] - buf2[0] * m[9] - buf2[3] * m[10],
				buf2[8] * m[11] + buf2[0] * m[8] + buf2[7] * m[10] - buf2[6] * m[10] - buf2[9] * m[11] - buf2[1] * m[8],
				buf2[6] * m[9] + buf2[11] * m[11] + buf2[3] * m[8] - buf2[10] * m[11] - buf2[2] * m[8] - buf2[7] * m[9],
				buf2[10] * m[10] + buf2[4] * m[8] + buf2[9] * m[9] - buf2[8] * m[9] - buf2[11] * m[10] - buf2[5] * m[8])
		val det = m[0] * result[0] + m[1] * result[1] + m[2] * result[2] + m[3] * result[3]
		return Matrix(result.map { it / det })
	}
}
