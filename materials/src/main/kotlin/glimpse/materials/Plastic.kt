package glimpse.materials

import glimpse.Color
import glimpse.Vector
import glimpse.cameras.Camera
import glimpse.gles.GLES
import glimpse.io.resource
import glimpse.models.Model
import glimpse.shaders.Program
import glimpse.shaders.shaderProgram

/**
 * Plastic material.
 */
class Plastic(val diffuse: Color, val ambient: Color = diffuse, val specular: Color = Color.WHITE, val shininess: Float = 1000f) : Material {

	companion object {
		fun init(gles: GLES) {
			PlasticShaderHelper.init(gles)
		}
	}

	override fun render(model: Model, camera: Camera) {
		val mvpMatrix = camera.cameraMatrix * model.transformation()
		val modelViewMatrix = camera.view.viewMatrix * model.transformation()
		PlasticShaderHelper.use()
		PlasticShaderHelper["u_DiffuseColor"] = diffuse
		PlasticShaderHelper["u_AmbientColor"] = ambient
		PlasticShaderHelper["u_SpecularColor"] = specular
		PlasticShaderHelper["u_Shininess"] = shininess
		PlasticShaderHelper["u_MVPMatrix"] = mvpMatrix
		PlasticShaderHelper["u_MVMatrix"] = modelViewMatrix
		PlasticShaderHelper["u_NormalMatrix"] = modelViewMatrix.inverse().transpose()
		PlasticShaderHelper["u_LightDirection"] = camera.view.viewMatrix * Vector(1f, 0f, 1f)
		PlasticShaderHelper["u_CameraPosition"] = camera.view.viewMatrix * camera.position
		PlasticShaderHelper.drawMesh(model.mesh)
	}

	override fun dispose() {
		PlasticShaderHelper.dispose()
	}
}

internal object PlasticShaderHelper : ShaderHelper() {

	override val program: Program by lazy {
		shaderProgram(gles) {
			vertexShader {
				PlasticShaderHelper.resource("Plastic_vertex.glsl").lines.joinToString(separator = "\n") { it }
			}
			fragmentShader {
				PlasticShaderHelper.resource("Plastic_fragment.glsl").lines.joinToString(separator = "\n") { it }
			}
		}
	}

	override val vertexPositionAttributeName = "a_VertexPosition"
	override val vertexTextureCoordinatesAttributeName = null
	override val vertexNormalAttributeName = "a_VertexNormal"
}
