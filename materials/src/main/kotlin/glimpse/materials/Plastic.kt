package glimpse.materials

import glimpse.Color
import glimpse.cameras.Camera
import glimpse.gles.delegates.DisposableLazyDelegate
import glimpse.io.resource
import glimpse.lights.Light
import glimpse.models.Model
import glimpse.shaders.Program
import glimpse.shaders.shaderProgram

/**
 * Plastic material.
 */
class Plastic(val diffuse: Color, val ambient: Color = diffuse, val specular: Color = Color.WHITE, val shininess: Float = 100f) : AbstractMaterial() {

	init {
		PlasticShaderHelper.registerDisposable()
	}

	override fun render(model: Model, camera: Camera, lights: List<Light>) {
		val mvpMatrix = camera.cameraMatrix * model.transformation()
		val viewMatrix = camera.view.viewMatrix
		val modelViewMatrix = viewMatrix * model.transformation()
		PlasticShaderHelper.use()
		PlasticShaderHelper["u_DiffuseColor"] = diffuse
		PlasticShaderHelper["u_AmbientColor"] = ambient
		PlasticShaderHelper["u_SpecularColor"] = specular
		PlasticShaderHelper["u_Shininess"] = shininess
		PlasticShaderHelper["u_MVPMatrix"] = mvpMatrix
		PlasticShaderHelper["u_MVMatrix"] = modelViewMatrix
		PlasticShaderHelper["u_ModelMatrix"] = model.transformation()
		PlasticShaderHelper["u_LightMatrix"] = viewMatrix.trimmed
		PlasticShaderHelper["u_NormalMatrix"] = modelViewMatrix.trimmed
		PlasticShaderHelper["u_Light"] = lights
		PlasticShaderHelper.drawMesh(model.mesh)
	}
}

internal object PlasticShaderHelper : ShaderHelper() {

	override val program: Program? by DisposableLazyDelegate {
		shaderProgram {
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
