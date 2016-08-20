package glimpse.api

/**
 * A point in three-dimensional space.
 *
 * @param x X coordinate.
 * @param y Y coordinate.
 * @param z Z coordinate.
 */
data class Point(val x: Float, val y: Float, val z: Float) {

	companion object {

		/**
		 * Origin of the coordinate system.
		 */
		val ORIGIN = Point(0f, 0f, 0f)

	}

	internal val _3f: Array<Float> by lazy { arrayOf(x, y, z) }
	internal val _4f: Array<Float> by lazy { arrayOf(x, y, z, 1f) }

	/**
	 * Returns a string representation of the [Point].
	 */
	override fun toString() = "(%.1f, %.1f, %.1f)".format(x, y, z)

	/**
	 * Returns a point translated by a [vector].
	 */
	infix fun translateBy(vector: Vector) = Point(x + vector.x, y + vector.y, z + vector.z)

	/**
	 * Returns a vector starting at this [Point] and ending at the [other] point.
	 */
	infix fun to(other: Point) = Vector(other.x - x, other.y - y, other.z - z)

	/**
	 * Converts the [Point] to a [Vector].
	 */
	fun toVector() = Vector(x, y, z)
}

/**
 * Returns a direct buffer containing values of X, Y, Z coordinates of points from the original list.
 */
fun List<Point>.toDirectBuffer() = toDirectFloatBuffer(size * 3) { it._3f }

/**
 * Returns a direct buffer containing values of X, Y, Z, 1 coordinates of augmented points from the original list.
 */
fun List<Point>.toDirectBufferAugmented() = toDirectFloatBuffer(size * 4) { it._4f }
