package glimpse.api

import io.kotlintest.specs.WordSpec

class SquareMatrixSpec : WordSpec() {

	init {

		"Determinant of a square matrix" should {
			"be 1 for identity" {
				forAll((1..8).toList()) { size ->
					SquareMatrix.identity(size).det == 1f
				}
			}
			"be equal to product of all numbers for a diagonal matrix" {
				forAll((1..8).toList()) { size ->
					SquareMatrix(size) { row, col -> if (row == col) (row + 1).toFloat() else 0f }.det ==
							(1..size).reduce { product, next -> product * next }.toFloat()
				}
			}
			"be 0 for a 'row + col + 1' matrix" {
				forAll((1..8).toList()) { size ->
					SquareMatrix(size) { row, col -> (row + col + 1).toFloat() }.det == 0f
				}
			}
		}

		"List representation of a square matrix" should {
			"contain all the elements of the matrix" {
				forAll((1..8).toList()) { size ->
					SquareMatrix(size) { row, col -> (row + col * size + 1).toFloat() }.asList() == (1..size).toList()
				}
			}
		}

		"Getting an element outside of the matrix" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					SquareMatrix.identity(4)[4, 2]
				}
				shouldThrow<IllegalArgumentException> {
					SquareMatrix.identity(4)[2, 4]
				}
				shouldThrow<IllegalArgumentException> {
					SquareMatrix.identity(4)[-1, 2]
				}
				shouldThrow<IllegalArgumentException> {
					SquareMatrix.identity(4)[2, -1]
				}
			}
		}

		"Multiplication of matrices of different sizes" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					SquareMatrix.identity(4) * SquareMatrix.identity(3)
				}
			}
		}

	}

}
