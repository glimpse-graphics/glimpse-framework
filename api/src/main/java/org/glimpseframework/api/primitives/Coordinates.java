package org.glimpseframework.api.primitives;

/**
 * Coordinates in two-dimensional space.
 *
 * <p>This class is immutable.</p>
 *
 * @author Slawomir Czerwinski
 */
public final class Coordinates {

	/**
	 * Creates new coordinates.
	 * @param x coordinate x
	 * @param y coordinate y
	 */
	public Coordinates(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets X coordinate.
	 * @return X coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets Y coordinate.
	 * @return Y coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets coordinates as an array of 2 float numbers.
	 * @return array of 2 float numbers
	 */
	public float[] get2f() {
		return new float[] {x, y};
	}

	private final float x, y;
}
