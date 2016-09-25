package glimpse.preview

import glimpse.*
import glimpse.Vector
import glimpse.cameras.camera
import glimpse.cameras.perspective
import glimpse.cameras.targeted
import glimpse.gles.BlendFactor
import glimpse.gles.DepthTestFunction
import glimpse.io.resource
import glimpse.jogl.*
import glimpse.lights.Light
import glimpse.materials.Material
import glimpse.materials.Plastic
import glimpse.materials.Textured
import glimpse.models.*
import glimpse.textures.Texture
import glimpse.textures.TextureMagnificationFilter
import glimpse.textures.TextureMinificationFilter
import glimpse.textures.readTexture
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

	fun transform(mesh: Mesh): Model = mesh.transform {
		val time = (Date().time / 50L) % 360L
		rotateZ(time.degrees)
		rotateX(-23.5.degrees)
	}

	var model = transform(sphere(16))

	val textures = mutableMapOf<Textured.TextureType, Texture>()

	val texturedMaterial = Textured { textureType -> textures[textureType]!! }
	val plasticMaterial = Plastic(Color.WHITE)

	var material: Material = plasticMaterial

	var lights = listOf<Light>(
			Light.DirectionLight(Vector(0f, 0f, -1f), Color.RED),
			Light.DirectionLight(Vector(-1f, 1f, 0f), Color.GREEN),
			Light.DirectionLight(Vector(-1f, -1f, 0f), Color.BLUE))

	glimpseFrame("Glimpse Framework Preview") {
		menuBar {
			menu("Mesh") {
				menuItem("Sphere") {
					onClick {
						model = transform(sphere(16))
					}
				}
				menuItem("Tetrahedron") {
					onClick {
						model = transform(tetrahedron())
					}
				}
				menuItem("Cube") {
					onClick {
						model = transform(cube())
					}
				}
			}
			menu("Material") {
				menuItem("Plastic") {
					onClick {
						material = plasticMaterial
					}
				}
				menuItem("Textured") {
					onClick {
						material = texturedMaterial
					}
				}
			}
			menu("Lights") {
				menuItem("Direction light") {
					onClick {
						lights = listOf(
								Light.DirectionLight(Vector(0f, 0f, -1f), Color.RED),
								Light.DirectionLight(Vector(-1f, 1f, 0f), Color.GREEN),
								Light.DirectionLight(Vector(-1f, -1f, 0f), Color.BLUE))
					}
				}
				menuItem("Point light") {
					onClick {
						lights = listOf(
								Light.PointLight(Point(0f, 0f, 6f), 20f, Color.MAGENTA),
								Light.PointLight(Point(12f, -12f, 0f), 20f, Color.YELLOW),
								Light.PointLight(Point(2f, 2f, 0f), 20f, Color.CYAN))
					}
				}
				menuItem("Spotlight") {
					onClick {
						lights = listOf(
								Light.Spotlight(Vector(5f, 23.5.degrees, Angle.RIGHT).toPoint(), Point.ORIGIN, 20.degrees, 100f, Color.RED),
								Light.Spotlight(Point(5f, -5f, 5f), Point.ORIGIN, 10.degrees, 100f, Color.GREEN),
								Light.Spotlight(Point(5f, 5f, -5f), Point.ORIGIN, 180.degrees, 100f, Color.BLUE))
					}
				}
			}
		}
		onInit {
			clearColor = Color.BLACK
			clearDepth = 1f
			isDepthTest = true
			depthTestFunction = DepthTestFunction.LESS_OR_EQUAL
			isBlend = true
			blendFunction = BlendFactor.SRC_ALPHA to BlendFactor.ONE_MINUS_SRC_ALPHA
			isCullFace = false
			textureMagnificationFilter = TextureMagnificationFilter.LINEAR
			textureMinificationFilter = TextureMinificationFilter.LINEAR_MIPMAP_LINEAR
			textures[Textured.TextureType.AMBIENT] = Context.resource("ambient.png").readTexture { withMipmap() }
			textures[Textured.TextureType.SPECULAR] = Context.resource("specular.png").readTexture { withMipmap() }
			textures[Textured.TextureType.DIFFUSE] = Context.resource("diffuse.png").readTexture { withMipmap() }
		}
		onResize { v ->
			viewport = v
			aspect = viewport.aspect
		}
		onRender {
			clearColorBuffer()
			clearDepthBuffer()
			material.render(model, camera, lights)
		}
		onDispose {
		}
	}
}

object Context
