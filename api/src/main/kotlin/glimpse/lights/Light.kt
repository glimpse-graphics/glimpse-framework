package glimpse.lights

import glimpse.Angle
import glimpse.Color
import glimpse.Point
import glimpse.Vector

sealed class Light(val type: Int, val color: Color) {

	class DirectionLight(val direction: Vector, color: Color = Color.WHITE) : Light(1, color)

	class PointLight(val position: Point, val distance: Float, color: Color = Color.WHITE) : Light(2, color)

	class Spotlight(val position: Point, val target: Point, val angle: Angle, val distance: Float, color: Color = Color.WHITE) : Light(3, color)
}
