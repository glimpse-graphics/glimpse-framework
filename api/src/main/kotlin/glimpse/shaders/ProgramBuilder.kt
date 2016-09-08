package glimpse.shaders

import glimpse.gles.GLES

/**
 * GLSL shader program builder.
 */
class ProgramBuilder(private val gles: GLES) {

	private val shaders = mutableListOf<Shader>()

	private operator fun ShaderType.invoke(source: () -> String) {
		val handle = gles.createShader(this)
		gles.compileShader(handle, source())
		if (!gles.getShaderCompileStatus(handle)) {
			throw ShaderCompileException(gles.getShaderLog(handle))
		}
		shaders.add(Shader(gles, this, handle))
	}

	/**
	 * Compiles a vertex shader.
	 *
	 * @param source Shader source code lambda.
	 */
	fun vertexShader(source: () -> String): Unit {
		ShaderType.VERTEX(source)
	}

	/**
	 * Compiles a fragment shader.
	 *
	 * @param source Shader source code lambda.
	 */
	fun fragmentShader(source: () -> String): Unit {
		ShaderType.FRAGMENT(source)
	}

	internal fun build(): Program {
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

/**
 * Returns a [Program], initialized with an [init] function and linked using given [gles] implementation.
 */
fun shaderProgram(gles: GLES, init: ProgramBuilder.() -> Unit): Program {
	val builder = ProgramBuilder(gles)
	builder.init()
	return builder.build()
}
