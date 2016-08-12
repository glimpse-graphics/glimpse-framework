package glimpse.api

/**
 * A point in three-dimensional space.
 *
 * @param x X coordinate.
 * @param y Y coordinate.
 * @param z Z coordinate.
 */
data class Point(val x: Float, val y: Float, val z: Float) {

	internal val _3f: Array<Float> by lazy { arrayOf(x, y, z) }
	internal val _4f: Array<Float> by lazy { arrayOf(x, y, z, 1f) }

	/**
	 * Returns a vector starting at this [Point] and ending at the [other] point.
	 */
	infix fun to(other: Point) = Vector(other.x - x, other.y - y, other.z - z)

	/**
	 * Converts the [Point] to a [Vector].
	 */
	fun toVector() = Vector(x, y, z)
}
