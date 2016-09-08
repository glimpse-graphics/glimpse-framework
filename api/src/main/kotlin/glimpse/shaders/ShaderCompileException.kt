package glimpse.shaders

import glimpse.api.GlimpseException

class ShaderCompileException(log: String) : GlimpseException("Shader compilation failed:\n$log")
