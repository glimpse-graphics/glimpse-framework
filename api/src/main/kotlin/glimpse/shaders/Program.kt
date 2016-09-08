package glimpse.shaders

import glimpse.gles.GLES

class Program(val gles: GLES, val handle: ProgramHandle, val shaders: List<Shader>) {

	internal var deleted = false;

	fun use() {
		assert(!deleted) { "Program deleted" }
		shaders.forEach { shader ->
			assert(!shader.deleted) { "Shader deleted" }
		}
		gles.useProgram(handle)
	}

	fun delete() {
		shaders.forEach { shader ->
			if (!shader.deleted) shader.delete()
		}
		deleted = true
		gles.deleteProgram(handle)
	}
}

data class ProgramHandle(val value: Int) {
	init {
		require(value != 0) { "Invalid program handle" }
	}
}
