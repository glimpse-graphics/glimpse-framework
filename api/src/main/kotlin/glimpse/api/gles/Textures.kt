package glimpse.api.gles

enum class TextureMinificationFilter {
	NEAREST,
	LINEAR,
	NEAREST_MIPMAP_NEAREST,
	LINEAR_MIPMAP_NEAREST,
	NEAREST_MIPMAP_LINEAR,
	LINEAR_MIPMAP_LINEAR
}

enum class TextureMagnificationFilter {
	NEAREST,
	LINEAR
}

enum class TextureWrapping {
	REPEAT,
	MIRRORED_REPEAT,
	CLAMP_TO_EDGE
}
