package glimpse.shaders

import glimpse.api.GlimpseException

class ProgramLinkException(log: String) : GlimpseException("Program linking failed:\n$log")
