package glimpse.lights

import glimpse.Angle
import glimpse.Color
import glimpse.Point
import glimpse.Vector

sealed class Light(val type: Int, val color: Color) {

	class DirectionLight(val direction: Vector, color: Color = Color.WHITE) : Light(1, color)

	class OmniLight(val position: Point, color: Color = Color.WHITE) : Light(2, color)

	class Spotlight(val position: Point, val target: Point, val angle: Angle, color: Color = Color.WHITE) : Light(3, color)
}
