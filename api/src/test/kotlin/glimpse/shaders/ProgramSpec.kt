package glimpse.shaders

import com.nhaarman.mockito_kotlin.verify
import glimpse.gles.GLES
import glimpse.test.GlimpseSpec

class ProgramSpec : GlimpseSpec() {

	init {

		"Using a program" should {
			"succeed if program is not deleted" {
				val glesMock = glesMock()
				val program = Program(ProgramHandle(3), createShaders())
				program.use()
				verify(glesMock).useProgram(ProgramHandle(3))
			}
			"cause an exception if program is deleted" {
				glesMock()
				val program = Program(ProgramHandle(3), createShaders())
				program.deleted = true
				shouldThrow<AssertionError> {
					program.use()
				}
			}
			"cause an exception if any shader is deleted" {
				glesMock()
				val program = Program(ProgramHandle(3), createShaders())
				program.shaders[0].deleted = true
				shouldThrow<AssertionError> {
					program.use()
				}
			}
		}

		"Deleting a program" should {
			"succeed" {
				val glesMock = glesMock()
				val program = Program(ProgramHandle(3), createShaders())
				program.delete()
				verify(glesMock).deleteProgram(ProgramHandle(3))
				program.deleted shouldBe true
			}
			"cause deleting both shaders" {
				val glesMock = glesMock()
				val program = Program(ProgramHandle(3), createShaders())
				program.delete()
				verify(glesMock).deleteShader(ShaderHandle(1))
				verify(glesMock).deleteShader(ShaderHandle(2))
				forAll(program.shaders) { program ->
					program.deleted shouldBe true
				}
			}
		}

	}

	private fun glesMock(): GLES = glesMock {}

	private fun createShaders() = listOf(
			Shader(ShaderType.VERTEX, ShaderHandle(1)),
			Shader(ShaderType.FRAGMENT, ShaderHandle(2)))
}
