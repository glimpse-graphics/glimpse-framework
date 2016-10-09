package glimpse.lights

import glimpse.Angle
import glimpse.Color
import glimpse.Point
import glimpse.Vector

/**
 * A single light source.
 *
 * @property type Light type identifier.
 * @property color Light color lambda.
 */
sealed class Light(val type: Int, val color: () -> Color) {

	/**
	 * Direction light source.
	 *
	 * @property direction Light direction lambda.
	 * @property color Light color lambda.
	 */
	class DirectionLight(
			val direction: () -> Vector,
			color: () -> Color = { Color.WHITE }) : Light(1, color)

	/**
	 * Point light source.
	 *
	 * @property position Light position lambda.
	 * @property distance Light maximum distance lambda.
	 * @property color Light color lambda.
	 */
	class PointLight(
			val position: () -> Point,
			val distance: () -> Float,
			color: () -> Color = { Color.WHITE }) : Light(2, color)

	/**
	 * Spotlight source.
	 *
	 * @property position Light position lambda.
	 * @property target Light cone target lambda.
	 * @property angle Light cone angle lambda.
	 * @property distance Light maximum distance lambda.
	 * @property color Light color lambda.
	 */
	class Spotlight(
			val position: () -> Point,
			val target: () -> Point,
			val angle: () -> Angle,
			val distance: () -> Float,
			color: () -> Color = { Color.WHITE }) : Light(3, color)
}
