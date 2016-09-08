package glimpse.shaders

import glimpse.Matrix
import glimpse.gles.GLES
import glimpse.models.Mesh
import glimpse.toDirectBufferAugmented

object PlainShaderProgram {

	private lateinit var gles: GLES
	private lateinit var program: Program

	fun mvpMatrix(matrix: () -> Matrix) {
		program.use()
		val location = gles.getUniformLocation(program.handle, "u_MVPMatrix")
		gles.uniformMatrix(location, matrix())
	}

	fun drawMesh(mesh: () -> Mesh) {
		program.use()
		val location = gles.getAttributeLocation(program.handle, "a_VertexPosition")
		val positions = mesh().positions
		val handle = gles.createAttributeFloatArray(location, positions.toDirectBufferAugmented(), 4)
		gles.enableAttributeArray(location)
		gles.drawTriangles(positions.size)
		gles.disableAttributeArray(location)
		gles.deleteAttributeArray(handle)
	}

	operator fun invoke(gles: GLES) {
		this.gles = gles
		program = shaderProgram(gles) {
			vertexShader {
				"""
				uniform mat4 u_MVPMatrix;
				attribute vec4 a_VertexPosition;

				void main() {
					gl_Position = u_MVPMatrix * a_VertexPosition;
				}
				""".trimIndent()
			}
			fragmentShader {
				"""
				void main() {
					float value = 1.0 - gl_FragCoord.z;
					gl_FragColor = vec4(value, value, value, 1.0);
				}
				""".trimIndent()
			}
		}
	}

	fun dispose() {
		program.delete()
	}
}
