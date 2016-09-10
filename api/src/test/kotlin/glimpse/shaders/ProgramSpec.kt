package glimpse.shaders

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import glimpse.gles.GLES
import glimpse.test.GlimpseSpec

class ProgramSpec : GlimpseSpec() {

	init {

		"Using a program" should {
			"succeed if program is not deleted" {
				val glesMock = createGLESMock()
				val program = Program(glesMock, ProgramHandle(3), createShaders(glesMock))
				program.use()
				verify(glesMock).useProgram(ProgramHandle(3));
			}
			"cause an exception if program is deleted" {
				val glesMock = createGLESMock()
				val program = Program(glesMock, ProgramHandle(3), createShaders(glesMock))
				program.deleted = true
				shouldThrow<AssertionError> {
					program.use()
				}
			}
			"cause an exception if any shader is deleted" {
				val glesMock = createGLESMock()
				val program = Program(glesMock, ProgramHandle(3), createShaders(glesMock))
				program.shaders[0].deleted = true
				shouldThrow<AssertionError> {
					program.use()
				}
			}
		}

		"Deleting a program" should {
			"succeed" {
				val glesMock = createGLESMock()
				val program = Program(glesMock, ProgramHandle(3), createShaders(glesMock))
				program.delete()
				verify(glesMock).deleteProgram(ProgramHandle(3));
				program.deleted shouldBe true
			}
			"cause deleting both shaders" {
				val glesMock = createGLESMock()
				val program = Program(glesMock, ProgramHandle(3), createShaders(glesMock))
				program.delete()
				verify(glesMock).deleteShader(ShaderHandle(1));
				verify(glesMock).deleteShader(ShaderHandle(2));
				forAll(program.shaders) { program ->
					program.deleted shouldBe true
				}
			}
		}

	}

	private fun createGLESMock(): GLES {
		return mock<GLES>()
	}

	private fun createShaders(gles: GLES) = listOf(
			Shader(gles, ShaderType.VERTEX, ShaderHandle(1)),
			Shader(gles, ShaderType.FRAGMENT, ShaderHandle(2)))
}
