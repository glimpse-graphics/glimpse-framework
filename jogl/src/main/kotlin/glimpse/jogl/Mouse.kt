package glimpse.jogl

import glimpse.Point
import java.awt.MouseInfo

/**
 * Returns mouse position with `x` and `y` mapped to the interval: -1…1.
 */
fun mousePosition(): Point {
	val x = MouseInfo.getPointerInfo().location.x.toFloat()
	val y = MouseInfo.getPointerInfo().location.y.toFloat()
	val width = MouseInfo.getPointerInfo().device.displayMode.width.toFloat()
	val height = MouseInfo.getPointerInfo().device.displayMode.height.toFloat()
	return Point(2f * x / width - 1f, 2f * y / height - 1f)
}
