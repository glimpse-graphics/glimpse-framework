package glimpse.shaders

import glimpse.gles.GLES

class Shader(val gles: GLES, val type: ShaderType, val handle: ShaderHandle) {

	internal var deleted = false;

	fun delete() {
		deleted = true
		gles.deleteShader(handle)
	}
}

data class ShaderHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid shader handle" }
	}
}
