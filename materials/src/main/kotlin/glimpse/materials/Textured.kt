package glimpse.materials

import glimpse.Point
import glimpse.Vector
import glimpse.cameras.Camera
import glimpse.io.resource
import glimpse.lights.Light
import glimpse.models.Model
import glimpse.shaders.Program
import glimpse.shaders.shaderProgram
import glimpse.textures.Texture

/**
 * Textured material.
 */
class Textured(val shininess: Float = 100f, val texture: (TextureType) -> Texture) : Material {

	enum class TextureType {
		AMBIENT,
		DIFFUSE,
		SPECULAR
	}

	init {
		TexturedShaderHelper.registerDisposable()
	}

	override fun render(model: Model, camera: Camera, lights: List<Light>) {
		val mvpMatrix = camera.cameraMatrix * model.transformation()
		val viewMatrix = camera.view.viewMatrix
		val modelViewMatrix = viewMatrix * model.transformation()
		TexturedShaderHelper.use()
		TexturedShaderHelper["u_DiffuseTexture", 0] = texture(TextureType.DIFFUSE)
		TexturedShaderHelper["u_AmbientTexture", 1] = texture(TextureType.AMBIENT)
		TexturedShaderHelper["u_SpecularTexture", 2] = texture(TextureType.SPECULAR)
		TexturedShaderHelper["u_Shininess"] = shininess
		TexturedShaderHelper["u_MVPMatrix"] = mvpMatrix
		TexturedShaderHelper["u_MVMatrix"] = modelViewMatrix
		TexturedShaderHelper["u_ModelMatrix"] = model.transformation()
		TexturedShaderHelper["u_LightMatrix"] = viewMatrix.trimmed
		TexturedShaderHelper["u_NormalMatrix"] = modelViewMatrix.trimmed
		TexturedShaderHelper["u_LightsCount"] = lights.size
		TexturedShaderHelper["u_LightType"] = lights.map { light -> light.type }.toIntArray()
		TexturedShaderHelper.setColors("u_LightColor", lights.map { light -> light.color })
		TexturedShaderHelper.setVectors("u_LightDirection", lights.map { light ->
			when(light) {
				is Light.DirectionLight -> light.direction
				else -> Vector.NULL
			}
		})
		TexturedShaderHelper.setPoints("u_LightPosition", lights.map { light ->
			when(light) {
				is Light.OmniLight -> light.position
				is Light.Spotlight -> light.position
				else -> Point.ORIGIN
			}
		})
		TexturedShaderHelper.drawMesh(model.mesh)
	}
}

internal object TexturedShaderHelper : ShaderHelper() {

	override val program: Program by lazy {
		shaderProgram {
			vertexShader {
				TexturedShaderHelper.resource("Textured_vertex.glsl").lines.joinToString(separator = "\n") { it }
			}
			fragmentShader {
				TexturedShaderHelper.resource("Textured_fragment.glsl").lines.joinToString(separator = "\n") { it }
			}
		}
	}

	override val vertexPositionAttributeName = "a_VertexPosition"
	override val vertexTextureCoordinatesAttributeName = "a_TextureCoordinates"
	override val vertexNormalAttributeName = "a_VertexNormal"
}
