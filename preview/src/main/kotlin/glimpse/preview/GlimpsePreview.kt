package glimpse.preview

import glimpse.*
import glimpse.Vector
import glimpse.cameras.camera
import glimpse.cameras.perspective
import glimpse.cameras.targeted
import glimpse.gles.BlendFactor
import glimpse.gles.DepthTestFunction
import glimpse.jogl.*
import glimpse.materials.Plastic
import glimpse.models.sphere
import java.util.*

fun main(args: Array<String>) {

	var aspect: Float = 1.333f

	val camera = camera {
		targeted {
			position { Vector(5f, 60.degrees, 0.degrees).toPoint() }
		}
		perspective {
			fov { 45.degrees }
			aspect { aspect }
			distanceRange(1f to 20f)
		}
	}

	val model = sphere(16).transform {
		val time = (Date().time / 30L) % 360L
		rotateZ(time.degrees)
	}

	val material = Plastic(Color.RED)

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
				}
				menuItem("Perspective") {
				}
				menuItem("Orthographic") {
				}
			}
		}
		onInit {
			Plastic.init(this)
			clearColor = Color.BLACK
			clearDepth = 1f
			isDepthTest = true
			depthTestFunction = DepthTestFunction.LESS_OR_EQUAL
			isBlend = true
			blendFunction = BlendFactor.SRC_ALPHA to BlendFactor.ONE_MINUS_SRC_ALPHA
			isCullFace = false
		}
		onResize { v ->
			viewport = v
			aspect = viewport.aspect
		}
		onRender {
			clearColorBuffer()
			clearDepthBuffer()
			material.render(model, camera)
		}
		onDispose {
			material.dispose()
		}
	}
}
