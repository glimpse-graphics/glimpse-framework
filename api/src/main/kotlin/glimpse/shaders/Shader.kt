package glimpse.shaders

import glimpse.gles.GLES
import glimpse.materials.GLESDelegate

/**
 * GLSL shader.
 *
 * @property type Shader type.
 * @property handle Shader handle.
 */
class Shader(val type: ShaderType, val handle: ShaderHandle) {

	internal val gles: GLES by GLESDelegate

	internal var deleted = false

	/**
	 * Tells GLES implementation to delete this shader.
	 */
	fun delete() {
		deleted = true
		gles.deleteShader(handle)
	}
}

/**
 * Shader handle.
 *
 * @property value Handle value.
 */
data class ShaderHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid shader handle: $value" }
	}
}
