package glimpse.api

/**
 * A vertex of a three-dimensional model.
 *
 * @param position Position in space.
 * @param textureCoordinates Texture coordinates.
 * @param normal Normal vector.
 */
data class Vertex(val position: Point, val textureCoordinates: TextureCoordinates, val normal: Vector)
