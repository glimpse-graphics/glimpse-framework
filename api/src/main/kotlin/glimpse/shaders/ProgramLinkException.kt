package glimpse.shaders

import glimpse.GlimpseException

/**
 * Shader program linking exception.
 */
class ProgramLinkException internal constructor(log: String) : GlimpseException("Program linking failed:\n$log")
