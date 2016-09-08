package glimpse.shaders

import glimpse.GlimpseException

class ProgramLinkException(log: String) : GlimpseException("Program linking failed:\n$log")
