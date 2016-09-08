package glimpse.gles

/**
 * Texture minification filter.
 */
enum class TextureMinificationFilter {
	NEAREST,
	LINEAR,
	NEAREST_MIPMAP_NEAREST,
	LINEAR_MIPMAP_NEAREST,
	NEAREST_MIPMAP_LINEAR,
	LINEAR_MIPMAP_LINEAR
}

/**
 * Texture magnification filter.
 */
enum class TextureMagnificationFilter {
	NEAREST,
	LINEAR
}

/**
 * Texture wrapping strategy.
 */
enum class TextureWrapping {
	REPEAT,
	MIRRORED_REPEAT,
	CLAMP_TO_EDGE
}
