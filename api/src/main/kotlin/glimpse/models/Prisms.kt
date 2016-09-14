package glimpse.models

import glimpse.TextureCoordinates
import glimpse.Vector

/**
 * Builds a prism with a given polygonal [base] and [height].
 */
fun prism(height: Float = 1f, base: Polygon): Mesh = mesh {
	val topCurve = base.curve.toFlatCurve(height)
	val bottomCurve = base.curve.toFlatCurve(-height)
	base.faces.forEach {
		it.toList().forEach {
			push(Vertex(topCurve.positions[it], topCurve.textureCoordinates[it], Vector.Z_UNIT))
		}
		it.toList().forEach {
			push(Vertex(bottomCurve.positions[it], bottomCurve.textureCoordinates[it], -Vector.Z_UNIT))
		}
	}
	(topCurve.segments zip bottomCurve.segments).forEach {
		quad(
				it.first.first.copy(textureCoordinates = TextureCoordinates.TOP_LEFT),
				it.second.first.copy(textureCoordinates = TextureCoordinates.BOTTOM_LEFT),
				it.second.second.copy(textureCoordinates = TextureCoordinates.BOTTOM_RIGHT),
				it.first.second.copy(textureCoordinates = TextureCoordinates.TOP_RIGHT))
	}
}

/**
 * Builds a prism with a given polygonal [base] and [height].
 */
fun prism(height: Float = 1f, base: () -> Polygon) = prism(height, base())
