package glimpse.preview

import glimpse.*
import glimpse.Vector
import glimpse.gles.BlendFactor
import glimpse.gles.DepthTestFunction
import glimpse.jogl.*
import glimpse.materials.Material
import glimpse.materials.Plastic
import glimpse.models.sphere
import java.util.*

fun main(args: Array<String>) {

	var projectionMatrix = perspectiveProjectionMatrix(120.degrees, 1.3f, 1f, 100f)

	fun viewMatrix(): Matrix {
		val time = (Date().time / 20L) % 360L
		val eye = Point.ORIGIN translateBy Vector(10f, 60.degrees, time.degrees)
		return lookAtViewMatrix(eye, Point.ORIGIN, Vector.Z_UNIT)
	}

	val model = sphere(8).transform {
	}

	val material = Plastic(Color.BLUE)

	glimpseFrame("Glimpse Framework Preview") {
		menuBar {
			menu("Mesh") {
				menuItem("Sphere") {
				}
				menuItem("Cube") {
				}
			}
			menu("Shader") {
				menuItem("Plain") {
				}
				menuItem("Textured") {
				}
			}
			menu("Textures") {
				menuItem("Ambient…") {
				}
				menuItem("Diffuse…") {
				}
				menuItem("Specular…") {
				}
				menuItem("Normal map…") {
				}
			}
			menu("Lenses") {
				menuItem("Frustum") {
					onClick { projectionMatrix = frustumProjectionMatrix(-1f, 1f, -.75f, .75f, 1f, 20f) }
				}
				menuItem("Perspective") {
					onClick { projectionMatrix = perspectiveProjectionMatrix(120.degrees, 1.3f, 1f, 100f) }
				}
				menuItem("Orthographic") {
					onClick { projectionMatrix = orthographicProjectionMatrix(-10f, 10f, -7.5f, 7.5f, -20f, 20f) }
				}
			}
		}
		onInit {
			Plastic.init(this)
			clearColor = Color.GRAY
			clearDepth = 1f
			isDepthTest = true
			depthTestFunction = DepthTestFunction.LESS_OR_EQUAL
			isBlend = true
			blendFunction = BlendFactor.SRC_ALPHA to BlendFactor.ONE_MINUS_SRC_ALPHA
			isCullFace = false
		}
		onResize { v ->
			viewport = v
			val aspect = viewport.width.toFloat() / viewport.height.toFloat()
			projectionMatrix = perspectiveProjectionMatrix(120.degrees, aspect, 1f, 100f)
		}
		onRender {
			clearColorBuffer()
			clearDepthBuffer()
			material.render(model, projectionMatrix * viewMatrix())
		}
		onDispose {
			material.dispose()
		}
	}
}
