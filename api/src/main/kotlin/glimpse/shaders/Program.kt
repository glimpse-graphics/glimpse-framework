package glimpse.shaders

import glimpse.gles.GLES
import glimpse.materials.GLESDelegate

/**
 * GLSL shader program.
 *
 * @property handle Program handle.
 * @property shaders Vertex and fragment shaders linked into the program.
 */
class Program(val handle: ProgramHandle, val shaders: List<Shader>) {

	internal val gles: GLES by GLESDelegate

	internal var deleted = false

	/**
	 * Tells GLES implementation to use this program.
	 */
	fun use() {
		assert(!deleted) { "Program deleted" }
		shaders.forEach { shader ->
			assert(!shader.deleted) { "Shader deleted" }
		}
		gles.useProgram(handle)
	}

	/**
	 * Tells GLES implementation to delete this program.
	 */
	fun delete() {
		shaders.forEach { shader ->
			if (!shader.deleted) shader.delete()
		}
		deleted = true
		gles.deleteProgram(handle)
	}
}

/**
 * Shader program handle.
 *
 * @property value Handle value.
 */
data class ProgramHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid program handle: $value" }
	}
}
