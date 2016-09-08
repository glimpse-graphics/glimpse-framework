package glimpse.gles

data class TextureHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid texture handle" }
	}
}

data class BufferHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid buffer handle" }
	}
}

data class UniformLocation(val value: Int) {
	init {
		require(value >= 0) { "Invalid uniform location" }
	}
}

data class AttributeLocation(val value: Int) {
	init {
		require(value >= 0) { "Invalid attribute location" }
	}
}

