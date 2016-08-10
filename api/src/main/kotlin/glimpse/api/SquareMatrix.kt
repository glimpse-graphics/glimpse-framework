package glimpse.api

internal class SquareMatrix(val size: Int, private val elements: (Int, Int) -> Float) {

	companion object {

		fun nullMatrix(size: Int) = SquareMatrix(size) { row, col -> 0f }

		fun identity(size: Int) = SquareMatrix(size) { row, col ->
			if (row == col) 1f else 0f
		}
	}

	operator fun get(row: Int, col: Int): Float {
		require(row in 0..size - 1) { "Row ${row} out of bounds: 0..${size - 1}" }
		require(col in 0..size - 1) { "Column ${col} out of bounds: 0..${size - 1}" }
		return elements(row, col)
	}

	/**
	 * Determinant of the matrix.
	 */
	val det: Float by lazy {
		if (size == 1) this[0, 0]
		else (0..size - 1).map { item -> this[0, item] * comatrix[0, item] }.sum()
	}

	private val comatrix: SquareMatrix by lazy {
		SquareMatrix(size) { row, col -> cofactor(row, col) }
	}

	private fun cofactor(row: Int, col: Int): Float =
			minor(row, col) * if ((row + col) % 2 == 0) 1f else -1f

	private fun minor(row: Int, col: Int): Float =
			sub(row, col).det

	private fun sub(delRow: Int, delCol: Int) = SquareMatrix(size - 1) { row, col ->
		this[if (row < delRow) row else row + 1, if (col < delCol) col else col + 1]
	}

	/**
	 * Adjugate matrix.
	 */
	val adj: SquareMatrix by lazy { comatrix.transpose() }

	fun transpose(): SquareMatrix = SquareMatrix(size) { row, col -> this[col, row] }

	fun inverse(): SquareMatrix = SquareMatrix(size) { row, col -> adj[row, col] / det }

	operator fun times(other: SquareMatrix): SquareMatrix {
		require(other.size == size) { "Cannot multiply matrices of different sizes." }
		return SquareMatrix(size) { row, col ->
			(0..size - 1).map {this[row, it] * other[it, col] }.sum()
		}
	}

	fun asList(): List<Float> = (0..size * size - 1).map { this[it % size, it / size] }

	fun asMatrix(): Matrix = Matrix(asList())
}

