package glimpse.gles

/**
 * Texture handle.
 *
 * @property value Handle value.
 */
data class TextureHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid texture handle: $value" }
	}
}

/**
 * Buffer handle.
 *
 * @property value Handle value.
 */
data class BufferHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid buffer handle: $value" }
	}
}

/**
 * Shader uniform location.
 *
 * @property value Location.
 */
data class UniformLocation(val value: Int) {
	init {
		require(value >= 0) { "Invalid uniform location: $value" }
	}
}

/**
 * Shader attribute location.
 *
 * @property value Location.
 */
data class AttributeLocation(val value: Int) {
	init {
		require(value >= 0) { "Invalid attribute location: $value" }
	}
}

