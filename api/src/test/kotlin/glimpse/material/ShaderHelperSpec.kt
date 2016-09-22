package glimpse.material

import com.nhaarman.mockito_kotlin.*
import glimpse.Color
import glimpse.Matrix
import glimpse.Point
import glimpse.Vector
import glimpse.gles.AttributeLocation
import glimpse.gles.BufferHandle
import glimpse.gles.GLES
import glimpse.gles.UniformLocation
import glimpse.materials.ShaderHelper
import glimpse.models.mesh
import glimpse.shaders.*
import glimpse.test.GlimpseSpec

class ShaderHelperSpec : GlimpseSpec() {

	init {
		"Shader helper" should {
			"be used" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper()
				helper.use()
				verify(glesMock).useProgram(ProgramHandle(3))
			}
			"be disposed" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper()
				helper.dispose()
				verify(glesMock).deleteProgram(ProgramHandle(3))
			}
			"set uniform float" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper()
				helper["uniform"] = 12.345f
				verify(glesMock).getUniformLocation(ProgramHandle(3), "uniform")
				verify(glesMock).uniformFloat(UniformLocation(10), 12.345f)
			}
			"set uniform int" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper()
				helper["uniform"] = 123
				verify(glesMock).getUniformLocation(ProgramHandle(3), "uniform")
				verify(glesMock).uniformInt(UniformLocation(10), 123)
			}
			"set uniform matrix" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper()
				helper["uniform"] = Matrix.IDENTITY
				verify(glesMock).getUniformLocation(ProgramHandle(3), "uniform")
				verify(glesMock).uniformMatrix(UniformLocation(10), Matrix.IDENTITY)
			}
			"set uniform vector" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper()
				helper["uniform"] = Vector.X_UNIT
				verify(glesMock).getUniformLocation(ProgramHandle(3), "uniform")
				verify(glesMock).uniformVector(UniformLocation(10), Vector.X_UNIT)
			}
			"set uniform point" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper()
				helper["uniform"] = Point(1f, 2f, 3f)
				verify(glesMock).getUniformLocation(ProgramHandle(3), "uniform")
				verify(glesMock).uniformPoint(UniformLocation(10), Point(1f, 2f, 3f))
			}
			"set uniform color" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper()
				helper["uniform"] = Color.MAGENTA
				verify(glesMock).getUniformLocation(ProgramHandle(3), "uniform")
				verify(glesMock).uniformColor(UniformLocation(10), Color.MAGENTA)
			}
			"draw a mesh with positions only" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper(vertexPositionAttributeName = "pos")
				helper.drawMesh(mesh { })
				verify(glesMock).getAttributeLocation(ProgramHandle(3), "pos")
				verify(glesMock, never()).getAttributeLocation(ProgramHandle(3), "coord")
				verify(glesMock, never()).getAttributeLocation(ProgramHandle(3), "norm")
				verify(glesMock).createAttributeFloatArray(eq(AttributeLocation(100)), any(), eq(4))
				verify(glesMock, times(1)).createAttributeFloatArray(any(), any(), any())
				verify(glesMock).enableAttributeArray(AttributeLocation(100))
				verify(glesMock, times(1)).enableAttributeArray(any())
				verify(glesMock).drawTriangles(0)
				verify(glesMock).disableAttributeArray(AttributeLocation(100))
				verify(glesMock, times(1)).disableAttributeArray(any())
				verify(glesMock).deleteAttributeArray(BufferHandle(500))
			}
			"draw a mesh with positions and normals" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper(vertexPositionAttributeName = "pos", vertexNormalAttributeName = "norm")
				helper.drawMesh(mesh { })
				verify(glesMock).getAttributeLocation(ProgramHandle(3), "pos")
				verify(glesMock, never()).getAttributeLocation(ProgramHandle(3), "coord")
				verify(glesMock).getAttributeLocation(ProgramHandle(3), "norm")
				verify(glesMock, times(2)).createAttributeFloatArray(any(), any(), any())
				verify(glesMock, times(2)).enableAttributeArray(any())
				verify(glesMock).drawTriangles(0)
				verify(glesMock, times(2)).disableAttributeArray(any())
				verify(glesMock, times(2)).deleteAttributeArray(BufferHandle(500))
			}
			"draw a mesh with positions, texture coordinates and normals" {
				val glesMock = glesMock()
				val helper = ConcreteShaderHelper("pos", "coord", "norm")
				helper.drawMesh(mesh { })
				verify(glesMock).getAttributeLocation(ProgramHandle(3), "pos")
				verify(glesMock).getAttributeLocation(ProgramHandle(3), "coord")
				verify(glesMock).getAttributeLocation(ProgramHandle(3), "norm")
				verify(glesMock, times(3)).createAttributeFloatArray(any(), any(), any())
				verify(glesMock, times(3)).enableAttributeArray(any())
				verify(glesMock).drawTriangles(0)
				verify(glesMock, times(3)).disableAttributeArray(any())
				verify(glesMock, times(3)).deleteAttributeArray(BufferHandle(500))
			}
		}

	}

	private fun glesMock(): GLES = glesMock {
		on { getUniformLocation(ProgramHandle(3), "uniform") } doReturn UniformLocation(10)
		on { getAttributeLocation(ProgramHandle(3), "pos") } doReturn AttributeLocation(100)
		on { getAttributeLocation(ProgramHandle(3), "coord") } doReturn AttributeLocation(200)
		on { getAttributeLocation(ProgramHandle(3), "norm") } doReturn AttributeLocation(300)
		on { createAttributeFloatArray(any(), any(), any()) } doReturn BufferHandle(500)
	}

	private fun createShaderProgram(): Program =
			Program(ProgramHandle(3), listOf(
					Shader(ShaderType.VERTEX, ShaderHandle(1)),
					Shader(ShaderType.VERTEX, ShaderHandle(2))))

	inner class ConcreteShaderHelper(
			override val vertexPositionAttributeName: String? = null,
			override val vertexTextureCoordinatesAttributeName: String? = null,
			override val vertexNormalAttributeName: String? = null) : ShaderHelper() {

		override val program = createShaderProgram()
	}
}
