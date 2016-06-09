package glimpse.api

/**
 * Two-dimensional angle.
 *
 * @property deg Angle measure in degrees.
 * @property rad Angle measure in radians.
 */
data class Angle private constructor(val deg: Float, val rad: Float) : Comparable<Angle> {

	companion object {

		/**
		 * Null angle.
		 */
		val NULL = Angle(0f, 0F)

		/**
		 * Full angle.
		 */
		val FULL = Angle(360f, (2.0 * Math.PI).toFloat())

		/**
		 * Straight angle.
		 */
		val STRAIGHT = FULL / 2f

		/**
		 * Right angle.
		 */
		val RIGHT = STRAIGHT / 2f

		/**
		 * Creates [Angle] from degrees.
		 */
		fun fromDeg(deg: Float) = Angle(deg, (deg * Math.PI / 180.0).toFloat())

		/**
		 * Creates [Angle] from radians.
		 */
		fun fromRad(rad: Float) = Angle((rad * 180.0 / Math.PI).toFloat(), rad)
	}

	/**
	 * Returns a string representation of the [Angle].
	 */
	override fun toString() = "%.1f\u00B0".format(deg)

	/**
	 * Returns an angle opposite to this angle.
	 */
	operator fun unaryMinus() = Angle(-deg, -rad)

	/**
	 * Returns a sum of this angle and the [other] angle.
	 */
	operator fun plus(other: Angle) = Angle(deg + other.deg, rad + other.rad)

	/**
	 * Returns a difference of this angle and the [other] angle.
	 */
	operator fun minus(other: Angle) = Angle(deg - other.deg, rad - other.rad)

	/**
	 * Returns a product of this angle and a [number].
	 */
	operator fun times(number: Float) = Angle(deg * number, rad * number)

	/**
	 * Returns a quotient of this angle and a [number].
	 */
	operator fun div(number: Float) = Angle(deg / number, rad / number)

	/**
	 * Returns a quotient of this angle and the [other] angle.
	 */
	operator fun div(other: Angle) = rad / other.rad

	/**
	 * Returns a remainder of dividing this angle by the [other] angle.
	 */
	operator fun mod(other: Angle) = Angle(deg % other.deg, rad % other.rad)

	/**
	 * Returns an angle coterminal to this angle, closest to [other] angle in clockwise direction.
	 */
	infix fun clockwiseFrom(other: Angle) =
			other - (other - this) % FULL - if (other < this) FULL else NULL

	/**
	 * Returns an angle coterminal to this angle, closest to [other] angle in counter-clockwise direction.
	 */
	infix fun counterClockwiseFrom(other: Angle) =
			other + (this - other) % FULL + if (other > this) FULL else NULL

	/**
	 * Compares this angle to the [other] angle.
	 *
	 * Returns zero if this angle is equal to the [other] angle,
	 * a negative number if it is less than [other],
	 * or a positive number if it is greater than [other].
	 */
	override operator fun compareTo(other: Angle) = rad.compareTo(other.rad)
}
