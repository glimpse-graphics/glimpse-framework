package glimpse.api

/**
 * A vector in three-dimensional space.
 *
 * @param x X coordinate.
 * @param y Y coordinate.
 * @param z Z coordinate.
 */
data class Vector(val x: Float, val y: Float, val z: Float) {

	/**
	 * Constructs a [Vector] from spherical coordinates.
	 *
	 * @param radius Radial coordinate.
	 * @param polarAngle Polar angle.
	 * @param azimuthAngle Azimuth angle.
	 */
	constructor(radius: Float, polarAngle: Angle, azimuthAngle: Angle) : this(
			radius * sin(polarAngle) * cos(azimuthAngle),
			radius * sin(polarAngle) * sin(azimuthAngle),
			radius * cos(polarAngle))

	companion object {

		/**
		 * Null vector.
		 */
		val NULL = Vector(0f, 0f, 0f)

		/**
		 * Unit vector in the direction of the X axis.
		 */
		val X_UNIT = Vector(1f, 0f, 0f)

		/**
		 * Unit vector in the direction of the Y axis.
		 */
		val Y_UNIT = Vector(0f, 1f, 0f)

		/**
		 * Unit vector in the direction of the Z axis.
		 */
		val Z_UNIT = Vector(0f, 0f, 1f)
	}

	internal val _3f: Array<Float> by lazy { arrayOf(x, y, z) }
	internal val _4f: Array<Float> by lazy { arrayOf(x, y, z, 1f) }

	/**
	 * Magnitude of the [Vector].
	 */
	val magnitude by lazy { Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat() }

	/**
	 * Polar angle of the vector in spherical coordinate system.
	 */
	val polarAngle by lazy { atan2(Vector(x, y, 0f).magnitude, z) }

	/**
	 * Azimuth angle of the vector in spherical coordinate system.
	 */
	val azimuthAngle by lazy { atan2(y, x) }

	/**
	 * Returns a unit vector in the direction of this [Vector].
	 */
	val normalize by lazy { normalize(1f) }

	/**
	 * Returns a vector opposit to this vector.
	 */
	operator fun unaryMinus() = Vector(-x, -y, -z)

	/**
	 * Returns a sum of this vector and the [other] vector.
	 */
	operator fun plus(other: Vector) = Vector(x + other.x, y + other.y, z + other.z)

	/**
	 * Returns a difference of this vector and the [other] vector.
	 */
	operator fun minus(other: Vector) = Vector(x - other.x, y - other.y, z - other.z)

	/**
	 * Returns a cross product of this vector and the [other] vector.
	 */
	operator fun times(other: Vector): Vector = Vector(
			y * other.z - z * other.y,
			z * other.x - x * other.z,
			x * other.y - y * other.x)

	/**
	 * Returns a dot product of this vector and the [other] vector.
	 */
	infix fun dot(other: Vector): Float = throw UnsupportedOperationException()

	/**
	 * Returns a product of this vector and the [number].
	 */
	operator fun times(number: Float) = Vector(x * number, y * number, z * number)

	/**
	 * Returns a quotient of this vector and the [number].
	 */
	operator fun div(number: Float) = Vector(x / number, y / number, z / number)

	/**
	 * Returns `true` if this vector is parallel to the [other] vector.
	 */
	infix fun parallelTo(other: Vector) = x * other.y == other.x * y && x * other.z == other.x * z && y * other.z == other.y * z

	/**
	 * Returns a vector of the given [magnitude], in the direction of this vector.
	 */
	fun normalize(magnitude: Float): Vector {
		require(magnitude > 0f) { "Cannot normalize a null vector. Division by 0." }
		return this * magnitude / this.magnitude
	}

	/**
	 * Converts the [Vector] to a [Point].
	 */
	fun toPoint() = Point(x, y, z)
}