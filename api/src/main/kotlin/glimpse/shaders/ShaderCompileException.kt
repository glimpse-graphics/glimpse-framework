package glimpse.shaders

import glimpse.GlimpseException

class ShaderCompileException(log: String) : GlimpseException("Shader compilation failed:\n$log")
