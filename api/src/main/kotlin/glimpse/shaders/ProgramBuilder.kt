package glimpse.shaders

import glimpse.gles.GLES

class ProgramBuilder(private val gles: GLES) {

	private val shaders = mutableListOf<Shader>()

	operator fun ShaderType.invoke(source: () -> String) {
		val handle = gles.createShader(this)
		gles.compileShader(handle, source())
		if (!gles.getShaderCompileStatus(handle)) {
			throw ShaderCompileException(gles.getShaderLog(handle))
		}
		shaders.add(Shader(gles, this, handle))
	}

	fun vertexShader(source: () -> String): Unit {
		ShaderType.VERTEX(source)
	}

	fun fragmentShader(source: () -> String): Unit {
		ShaderType.FRAGMENT(source)
	}

	fun build(): Program {
		val handle = gles.createProgram()
		shaders.forEach { shader ->
			gles.attachShader(handle, shader.handle)
		}
		gles.linkProgram(handle)
		if (!gles.getProgramLinkStatus(handle)) {
			throw ProgramLinkException(gles.getProgramLog(handle))
		}
		return Program(gles, handle, shaders.toList())
	}
}

fun shaderProgram(gles: GLES, init: ProgramBuilder.() -> Unit): Program {
	val builder = ProgramBuilder(gles)
	builder.init()
	return builder.build()
}