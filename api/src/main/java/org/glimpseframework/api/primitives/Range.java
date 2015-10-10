package org.glimpseframework.api.primitives;

import java.util.Objects;

/**
 * Range of floating point values.
 * @author Slawomir Czerwinski
 */
public final class Range {

	/**
	 * Creates a new range.
	 * @param begin range begin value
	 * @param end range end value
	 */
	public Range(float begin, float end) {
		this.begin = begin;
		this.end = end;
	}

	/**
	 * Gets range begin value.
	 * @return range begin value
	 */
	public float getBegin() {
		return begin;
	}

	/**
	 * Gets range end value.
	 * @return range end value
	 */
	public float getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return String.format("[%.1f\u2026%.1f]", begin, end);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		Range range = (Range) object;
		return (Float.compare(range.begin, begin) == 0) && (Float.compare(range.end, end) == 0);
	}

	@Override
	public int hashCode() {
		return Objects.hash(begin, end);
	}

	private final float begin;
	private final float end;
}
