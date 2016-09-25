package glimpse.shaders

import com.nhaarman.mockito_kotlin.*
import glimpse.gles.GLES
import glimpse.test.GlimpseSpec

class ProgramBuilderSpec : GlimpseSpec() {

	init {

		"Program builder" should {
			"create both shaders" {
				val glesMock = glesMock()
				buildShaderProgram()
				verify(glesMock).createShader(ShaderType.VERTEX)
				verify(glesMock).createShader(ShaderType.FRAGMENT)
			}
			"compile both shaders" {
				val glesMock = glesMock()
				buildShaderProgram()
				verify(glesMock).compileShader(ShaderHandle(1), "vertex shader code")
				verify(glesMock).compileShader(ShaderHandle(2), "fragment shader code")
			}
			"cause exception if compilation fails" {
				glesMock {
					on { createShader(any()) } doReturn ShaderHandle(1) doReturn ShaderHandle(2)
					on { getShaderCompileStatus(ShaderHandle(1)) } doReturn true
					on { getShaderCompileStatus(ShaderHandle(2)) } doReturn false
					on { getShaderLog(ShaderHandle(2)) } doReturn "Error"
				}
				shouldThrow<ShaderCompileException> {
					buildShaderProgram()
				}
			}
			"create a program" {
				val glesMock = glesMock()
				buildShaderProgram()
				verify(glesMock).createProgram()
			}
			"attach both shaders to program" {
				val glesMock = glesMock()
				buildShaderProgram()
				verify(glesMock).attachShader(ProgramHandle(3), ShaderHandle(1))
				verify(glesMock).attachShader(ProgramHandle(3), ShaderHandle(2))
			}
			"link a program" {
				val glesMock = glesMock()
				buildShaderProgram()
				verify(glesMock).linkProgram(ProgramHandle(3))
			}
			"return a program with correct data" {
				val glesMock = glesMock()
				val program = buildShaderProgram()
				program.gles shouldBe glesMock
				program.handle shouldBe ProgramHandle(3)
				program.shaders should haveSize(2)
				program.shaders.map { it.gles }.filter { it != glesMock } should beEmpty()
				program.shaders.map { it.type } should containInAnyOrder(*ShaderType.values())
				program.shaders.map { it.handle } should containInAnyOrder(ShaderHandle(1), ShaderHandle(2))
			}
			"cause exception if linking fails" {
				glesMock {
					on { createShader(any()) } doReturn ShaderHandle(1) doReturn ShaderHandle(2)
					on { getShaderCompileStatus(ShaderHandle(1)) } doReturn true
					on { getShaderCompileStatus(ShaderHandle(2)) } doReturn true
					on { createProgram() } doReturn ProgramHandle(3)
					on { getProgramLinkStatus(ProgramHandle(3)) } doReturn false
					on { getProgramLog(ProgramHandle(3)) } doReturn "Error"
				}
				shouldThrow<ProgramLinkException> {
					buildShaderProgram()
				}
			}
		}

	}

	private fun glesMock(): GLES = glesMock {
		on { createShader(any()) } doReturn ShaderHandle(1) doReturn ShaderHandle(2)
		on { getShaderCompileStatus(ShaderHandle(1)) } doReturn true
		on { getShaderCompileStatus(ShaderHandle(2)) } doReturn true
		on { createProgram() } doReturn ProgramHandle(3)
		on { getProgramLinkStatus(ProgramHandle(3)) } doReturn true
	}

	private fun buildShaderProgram(): Program = shaderProgram {
		vertexShader {
			"vertex shader code"
		}
		fragmentShader {
			"fragment shader code"
		}
	}
}
