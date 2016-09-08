package glimpse

/**
 * Range of angles.
 */
data class AnglesRange(override val start: Angle, override val endInclusive: Angle): ClosedRange<Angle> {

	/**
	 * Returns an even partition of the range split in [count] parts.
	 */
	infix fun partition(count: Int) =
			(0..count).map { it.toFloat() / count.toFloat() }.map { start + (endInclusive - start) * it }
}
