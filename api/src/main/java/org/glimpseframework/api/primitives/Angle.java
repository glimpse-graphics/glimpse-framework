package org.glimpseframework.api.primitives;

import java.util.Objects;

/**
 * Two-dimensional angle.
 *
 * <p>This class is immutable.</p>
 *
 * @author Slawomir Czerwinski
 */
public final class Angle implements Comparable<Angle> {

	/**
	 * Angle direction.
	 */
	public enum Direction {
		CLOCKWISE,
		COUNTER_CLOCKWISE,
	}

	/**
	 * Creates an angle of a specific measure in degrees.
	 * @param degrees angle measure in degrees
	 * @return new angle
	 */
	public static Angle fromDegrees(float degrees) {
		return new Angle(degrees, degreesToRadians(degrees));
	}

	private static float degreesToRadians(float degrees) {
		return Double.valueOf(degrees * Math.PI / 180.0).floatValue();
	}

	/**
	 * Creates an angle of a specific measure in radians.
	 * @param radians angle measure in radians
	 * @return new angle
	 */
	public static Angle fromRadians(float radians) {
		return new Angle(radiansToDegrees(radians), radians);
	}

	private static float radiansToDegrees(float radians) {
		return Double.valueOf(radians * 180.0 / Math.PI).floatValue();
	}

	private Angle(float degrees, float radians) {
		this.degrees = degrees;
		this.radians = radians;
	}

	/**
	 * Gets angle size in degrees.
	 * @return angle size in degrees
	 */
	public float getDegrees() {
		return degrees;
	}

	/**
	 * Gets angle size in degrees.
	 * @return angle size in radians
	 */
	public float getRadians() {
		return radians;
	}

	/**
	 * Gets angle coterminal with this angle, closest to reference angle in requested direction.
	 * @param reference the reference angle
	 * @param direction direction
	 * @return angle coterminal with this angle
	 */
	public Angle getCoterminalNextTo(Angle reference, Direction direction) {
		double turns = 0.0;
		switch (direction) {
			case CLOCKWISE:
				turns = -Math.floor((getRadians() - reference.getRadians()) * 0.5 / Math.PI) - 1.0;
				break;
			case COUNTER_CLOCKWISE:
				turns = Math.floor((reference.getRadians() - getRadians()) * 0.5 / Math.PI) + 1.0;
				break;
			default:
				break;
		}
		return Angle.fromRadians(getRadians() + Double.valueOf((turns) * 2.0 * Math.PI).floatValue());
	}

	@Override
	public String toString() {
		return String.format("%.1f\u00B0", degrees);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		return Float.compare(((Angle) object).radians, radians) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(radians);
	}

	@Override
	public int compareTo(Angle angle) {
		return Float.compare(radians, angle.radians);
	}

	private static final float RIGHT_ANGLE_RADIANS = Double.valueOf(0.5 * Math.PI).floatValue();
	private static final float STRAIGHT_ANGLE_RADIANS = Double.valueOf(Math.PI).floatValue();
	private static final float FULL_ANGLE_RADIANS = Double.valueOf(2.0 * Math.PI).floatValue();

	/** Null angle (0&deg;). */
	public static final Angle NULL_ANGLE = fromRadians(0.0f);

	/** Right angle (90&deg;). */
	public static final Angle RIGHT_ANGLE = fromRadians(RIGHT_ANGLE_RADIANS);

	/** Straight angle (180&deg;). */
	public static final Angle STRAIGHT_ANGLE = fromRadians(STRAIGHT_ANGLE_RADIANS);

	/** Full angle (360&deg;). */
	public static final Angle FULL_ANGLE = fromRadians(FULL_ANGLE_RADIANS);

	private final float degrees;
	private final float radians;
}
