package glimpse

/**
 * Matrix defining three-dimensional affine transformations.
 */
data class Matrix(private val matrix: List<Float>) {

	companion object {

		/**
		 * Identity matrix.
		 */
		val IDENTITY = SquareMatrix.identity(4).asMatrix()

		/**
		 * Null matrix.
		 */
		val NULL = SquareMatrix.nullMatrix(4).asMatrix()
	}

	init {
		require(matrix.size === 16) {
			"Matrix must consist of exactly 16 (4×4) elements. ${matrix.size} elements were provided instead."
		}
	}

	internal val _16f : Array<Float> by lazy { matrix.toTypedArray() }

	internal val squareMatrix: SquareMatrix by lazy { SquareMatrix(4) { row, col -> this[row, col] } }

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
	operator fun times(other: Matrix): Matrix = (squareMatrix * other.squareMatrix).asMatrix()

	/**
	 * Multiplies this matrix by a [vector].
	 */
	operator fun times(vector: Vector): Vector {
		val v = timesArray(vector._4f)
		return Vector(v[0], v[1], v[2])
	}

	private fun timesArray(array: Array<Float>): List<Float> =
			(0..3).map { row ->
				(0..3).map { col ->
					this[row, col] * array[col]
				}.sum()
			}

	/**
	 * Multiplies this matrix by a [point].
	 */
	operator fun times(point: Point): Point {
		val p = timesArray(point._4f)
		return Point(p[0], p[1], p[2])
	}

	/**
	 * Returns a transposed matrix.
	 */
	fun transpose(): Matrix = squareMatrix.transpose().asMatrix()

	/**
	 * Returns an inverted matrix.
	 */
	fun inverse(): Matrix = squareMatrix.inverse().asMatrix()
}
