package glimpse.materials

import glimpse.Color
import glimpse.Matrix
import glimpse.Vector
import glimpse.gles.GLES
import glimpse.models.Model
import glimpse.shaders.Program
import glimpse.shaders.shaderProgram

/**
 * Plastic material.
 */
class Plastic(val diffuse: Color, val ambient: Color = diffuse, val shininess: Float = 1f, val specularExponent: Float = 2f) : Material {

	companion object {
		fun init(gles: GLES) {
			PlasticShaderHelper.init(gles)
		}
	}

	override fun render(model: Model, vpMatrix: Matrix) {
		val mvpMatrix = vpMatrix * model.modelMatrix
		PlasticShaderHelper.use()
		PlasticShaderHelper["u_DiffuseColor"] = diffuse
		PlasticShaderHelper["u_AmbientColor"] = ambient
		PlasticShaderHelper["u_Shininess"] = shininess
		PlasticShaderHelper["u_SpecularExponent"] = specularExponent
		PlasticShaderHelper["u_MVPMatrix"] = mvpMatrix
		PlasticShaderHelper["u_NormalMatrix"] = mvpMatrix.inverse().transpose()
		PlasticShaderHelper["u_LightPosition"] = Vector(20f, 20f, 20f)
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
				"""
				uniform mat4 u_MVPMatrix;
				uniform mat4 u_NormalMatrix;

				uniform vec4 u_LightPosition;

				attribute vec4 a_VertexPosition;
				attribute vec4 a_VertexNormal;

				varying vec3 v_Normal;
				varying vec3 v_LightDirection;

				void main() {
					vec4 position = u_MVPMatrix * a_VertexPosition;
					vec4 lightPosition = u_MVPMatrix * u_LightPosition;

					v_Normal = normalize(u_NormalMatrix * a_VertexNormal).xyz;
					v_LightDirection = normalize(vec3(lightPosition - position));

					gl_Position = position;
				}
				""".trimIndent()
			}
			fragmentShader {
				"""
				uniform vec4 u_DiffuseColor;
				uniform vec4 u_AmbientColor;

				uniform float u_Shininess;
				uniform float u_SpecularExponent;

				varying vec3 v_Normal;
				varying vec3 v_LightDirection;

				void main() {
					float diffuseValue = max(0.0, dot(v_Normal, v_LightDirection));
					float specularValue = u_Shininess * pow(max(0.0, dot(reflect(-v_LightDirection, v_Normal), v_LightDirection)), u_SpecularExponent);
					vec3 diffuse = u_DiffuseColor.rgb * 0.5 * diffuseValue;
					vec3 ambient = u_AmbientColor.rgb * 0.2;
					vec3 specular = vec3(1.0, 1.0, 1.0) * specularValue;

					gl_FragColor = vec4(specular + diffuse + ambient, u_DiffuseColor.a);
				}
				""".trimIndent()
			}
		}
	}

	override val vertexPositionAttributeName = "a_VertexPosition"
	override val vertexTextureCoordinatesAttributeName = null
	override val vertexNormalAttributeName = "a_VertexNormal"
}
