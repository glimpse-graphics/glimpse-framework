package org.glimpseframework.api.primitives;

import java.util.Objects;

/**
 * A vector in three-dimensional space, supporting affine transformations.
 *
 * <p>This class is immutable.</p>
 *
 * @author Slawomir Czerwinski
 */
public final class Vector {

	/**
	 * Creates a new vector.
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param z coordinate z
	 */
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a vector connecting two given points.
	 * @param start start point
	 * @param end end point
	 */
	public Vector(Point start, Point end) {
		this.x = end.getX() - start.getX();
		this.y = end.getY() - start.getY();
		this.z = end.getZ() - start.getZ();
	}

	/**
	 * Creates a vector from spherical coordinates.
	 * @param radius vector magnitude
	 * @param polarAngle vector polar angle
	 * @param azimuthAngle vector azimuth angle
	 * @return created vector
	 */
	public static Vector fromSphericalCoordinates(float radius, Angle polarAngle, Angle azimuthAngle) {
		return new Vector(
				Double.valueOf(radius * Math.sin(polarAngle.getRadians()) * Math.cos(azimuthAngle.getRadians()))
						.floatValue(),
				Double.valueOf(radius * Math.sin(polarAngle.getRadians()) * Math.sin(azimuthAngle.getRadians()))
						.floatValue(),
				Double.valueOf(radius * Math.cos(polarAngle.getRadians()))
						.floatValue());
	}

	/**
	 * Gets X coordinate of the vector.
	 * @return X coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets Y coordinate of the vector.
	 * @return Y coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets Z coordinate of the vector.
	 * @return Z coordinate
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Gets magnitude of the vector.
	 * @return vector's magnitude
	 */
	public float getMagnitude() {
		return Double.valueOf(Math.sqrt(x * x + y * y + z * z)).floatValue();
	}

	/**
	 * Gets polar angle of the vector.
	 * @return vector's polar angle
	 */
	public Angle getPolarAngle() {
		return Angle.fromRadians(Double.valueOf(Math.atan2(Math.sqrt(x * x + y * y), z)).floatValue());
	}

	/**
	 * Gets azimuth angle of the vector.
	 * @return vector's azimuth angle
	 */
	public Angle getAzimuthAngle() {
		return Angle.fromRadians(Double.valueOf(Math.atan2(y, x)).floatValue());
	}

	/**
	 * Checks whether the vector is parallel to another vector.
	 * @param v second vector
	 * @return {@code true} if the vectors are parallel; otherwise returns {@code false}
	 */
	public boolean isParallelTo(Vector v) {
		return (x * v.y == v.x * y) && (x * v.z == v.x * z) && (y * v.z == v.y * z);
	}

	/**
	 * Gets a vector with the same direction, but with a magnitude of 1.
	 * @return vector with a magnitude of 1
	 * @throws NullVectorNormalizationException if vector's magnitude is 0.
	 */
	public Vector normalize() throws NullVectorNormalizationException {
		return normalize(1.0f);
	}

	/**
	 * Gets a vector with the same direction, but with a given magnitude.
	 * <p>If the {@code magnitude} argument is negative,
	 * the direction of the resulting vector will be changed to opposite.</p>
	 * @param magnitude magnitude of the new vector
	 * @return vector with the given magnitude
	 * @throws NullVectorNormalizationException if vector's magnitude is 0.
	 */
	public Vector normalize(float magnitude) throws NullVectorNormalizationException {
		if (equals(NULL_VECTOR)) {
			throw new NullVectorNormalizationException();
		}
		return multiply(magnitude / getMagnitude());
	}

	/**
	 * Creates a new vector, being a result of scalar multiplication.
	 * <p>The new vector has the same direction as the original vector, but its magnitude is multiplied
	 * by {@code scalar}.</p>
	 * <p>If the {@code scalar} argument is negative, the direction will be changed to opposite.</p>
	 * @param scalar multiplication factor
	 * @return vector with the multiplied magnitude
	 */
	public Vector multiply(float scalar) {
		return new Vector(scalar * x, scalar * y, scalar * z);
	}

	/**
	 * Creates a vector being a cross product of two vectors.
	 * @param v second vector
	 * @return cross product of two vectors
	 */
	public Vector crossProduct(Vector v) {
		return new Vector(
				y * v.z - z * v.y,
				z * v.x - x * v.z,
				x * v.y - y * v.x);
	}

	/**
	 * Gets vector coordinates as an array of 3 float numbers.
	 * @return array of 3 float numbers
	 */
	public float[] get3f() {
		return new float[] {x, y, z};
	}

	/**
	 * Gets vector coordinates in augmented form, as an array of 4 float numbers.
	 * <p>4th number is always 1.0f.</p>
	 * @return array of 4 float numbers
	 */
	public float[] get4f() {
		return new float[] {x, y, z, 1.0f};
	}

	@Override
	public String toString() {
		return String.format("[%.1f %.1f %.1f 1.0]", x, y, z);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Vector vector = (Vector) o;

		if (Float.compare(vector.x, x) != 0) {
			return false;
		}
		if (Float.compare(vector.y, y) != 0) {
			return false;
		}
		if (Float.compare(vector.z, z) != 0) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	/** A null vector. */
	public static final Vector NULL_VECTOR = new Vector(0.0f, 0.0f, 0.0f);

	/** A unit vector parallel to X-axis. */
	public static final Vector X_UNIT_VECTOR = new Vector(1.0f, 0.0f, 0.0f);

	/** A unit vector parallel to Y-axis. */
	public static final Vector Y_UNIT_VECTOR = new Vector(0.0f, 1.0f, 0.0f);

	/** A unit vector parallel to Z-axis. */
	public static final Vector Z_UNIT_VECTOR = new Vector(0.0f, 0.0f, 1.0f);

	private final float x, y, z;
}
