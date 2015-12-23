package org.glimpseframework.api.primitives;

import java.util.Objects;

/**
 * RGBA color.
 *
 * <p>This class is immutable.</p>
 *
 * @author Slawomir Czerwinski
 */
public final class Color {

	/**
	 * Creates a fully opaque color.
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public Color(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}

	/**
	 * Creates a color with transparency.
	 * @param r red
	 * @param g green
	 * @param b blue
	 * @param alpha alpha channel
	 */
	public Color(float r, float g, float b, float alpha) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
	}

	/**
	 * Gets value of red channel.
	 * @return value of red channel
	 */
	public float getR() {
		return r;
	}

	/**
	 * Gets value of green channel.
	 * @return value of green channel
	 */
	public float getG() {
		return g;
	}

	/**
	 * Gets value of blue channel.
	 * @return value of blue channel
	 */
	public float getB() {
		return b;
	}

	/**
	 * Gets value of alpha channel.
	 * @return value of alpha channel
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * Gets color channels, except alpha channel, as an array of 3 floating point numbers.
	 * @return array of 3 float numbers
	 */
	public float[] get3f() {
		return new float[] {r, g, b};
	}

	/**
	 * Gets color channels, with alpha channel, as an array of 4 floating point numbers.
	 * @return array of 4 float numbers
	 */
	public float[] get4f() {
		return new float[] {r, g, b, alpha};
	}

	@Override
	public String toString() {
		return String.format("#%02x%02x%02x%02x",
				channelToInt(alpha), channelToInt(r), channelToInt(g), channelToInt(b));
	}

	private int channelToInt(float value) {
		return Float.valueOf(255.0f * value).intValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Color color = (Color) o;

		if (Float.compare(color.alpha, alpha) != 0) {
			return false;
		}
		if (Float.compare(color.b, b) != 0) {
			return false;
		}
		if (Float.compare(color.g, g) != 0) {
			return false;
		}
		if (Float.compare(color.r, r) != 0) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alpha, r, g, b);
	}

	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f);
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);

	public static final Color RED = new Color(1.0f, 0.0f, 0.0f);
	public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f);
	public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f);

	public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f);
	public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f);
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f);

	private final float r, g, b;
	private final float alpha;
}
