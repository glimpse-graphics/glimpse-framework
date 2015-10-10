package org.glimpseframework.api.primitives;

import java.util.Objects;

/**
 * A point representation in three-dimensional space, supporting affine transformations.
 * @author Slawomir Czerwinski
 */
public final class Point {

	/**
	 * Creates a new 3D point.
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param z coordinate z
	 */
	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	/**
	 * Gets point coordinates as an array of 3 float numbers.
	 * @return array of 3 float numbers
	 */
	public float[] get3f() {
		return new float[]{x, y, z};
	}

	/**
	 * Gets point coordinates in augmented form, as an array of 4 float numbers.
	 * <p>4th number is always 1.0f.</p>
	 * @return array of 4 float numbers
	 */
	public float[] get4f() {
		return new float[]{x, y, z, 1.0f};
	}

	/**
	 * Translates the point by vector and returns the result.
	 * @param v translation vector
	 * @return point after translation
	 */
	public Point translate(Vector v) {
		return new Point(x + v.getX(), y + v.getY(), z + v.getZ());
	}

	@Override
	public String toString() {
		return String.format("(%.1f %.1f %.1f 1.0)", x, y, z);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Point point = (Point) o;

		if (Float.compare(point.x, x) != 0) {
			return false;
		}
		if (Float.compare(point.y, y) != 0) {
			return false;
		}
		if (Float.compare(point.z, z) != 0) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	/** Origin of the coordinate system. */
	public static final Point ORIGIN = new Point(0.0f, 0.0f, 0.0f);

	private final float x, y, z;
}
