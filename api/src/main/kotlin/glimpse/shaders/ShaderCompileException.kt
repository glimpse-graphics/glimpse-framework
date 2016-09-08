package glimpse.shaders

import glimpse.GlimpseException

/**
 * Shader compilation exception.
 */
class ShaderCompileException internal constructor(log: String) : GlimpseException("Shader compilation failed:\n$log")
