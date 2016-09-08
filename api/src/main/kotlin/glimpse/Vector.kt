package glimpse

import glimpse.buffers.toDirectFloatBuffer

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
	 * @param inclination Inclination.
	 * @param azimuth Azimuth.
	 */
	constructor(radius: Float, inclination: Angle, azimuth: Angle) : this(
			radius * sin(inclination) * cos(azimuth),
			radius * sin(inclination) * sin(azimuth),
			radius * cos(inclination))

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
	 * Returns a string representation of the [Vector].
	 */
	override fun toString() = "[%.1f, %.1f, %.1f]".format(x, y, z)

	/**
	 * Magnitude of the [Vector].
	 */
	val magnitude by lazy { Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat() }

	/**
	 * Inclination of the vector in spherical coordinate system.
	 */
	val inclination by lazy { atan2(Vector(x, y, 0f).magnitude, z) }

	/**
	 * Azimuth of the vector in spherical coordinate system.
	 */
	val azimuth by lazy { atan2(y, x) }

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
	infix fun dot(other: Vector): Float = _3f.zip(other._3f).map { it.first * it.second }.sum()

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
	infix fun parallelTo(other: Vector) = this * other == NULL

	/**
	 * Returns a vector of the given [magnitude], in the direction of this vector.
	 */
	fun normalize(magnitude: Float): Vector {
		require(this.magnitude > 0f) { "Cannot normalize a null vector. Division by 0." }
		return this * magnitude / this.magnitude
	}

	/**
	 * Converts the [Vector] to a [Point].
	 */
	fun toPoint() = Point(x, y, z)
}

/**
 * Returns a direct buffer containing values of X, Y, Z coordinates of vectors from the original list.
 */
fun List<Vector>.toDirectBuffer() = toDirectFloatBuffer(size * 3) { it._3f }

/**
 * Returns a direct buffer containing values of X, Y, Z, 1 coordinates of augmented vectors from the original list.
 */
fun List<Vector>.toDirectBufferAugmented() = toDirectFloatBuffer(size * 4) { it._4f }
