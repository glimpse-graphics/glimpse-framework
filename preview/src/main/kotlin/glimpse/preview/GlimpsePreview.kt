package glimpse.preview

import glimpse.*
import glimpse.Vector
import glimpse.cameras.camera
import glimpse.cameras.perspective
import glimpse.cameras.targeted
import glimpse.gles.BlendFactor
import glimpse.gles.DepthTestFunction
import glimpse.io.properties
import glimpse.io.resource
import glimpse.jogl.*
import glimpse.jogl.io.openImageFile
import glimpse.jogl.io.openOBJFile
import glimpse.lights.Light
import glimpse.lights.directionLight
import glimpse.lights.pointLight
import glimpse.lights.spotlight
import glimpse.materials.Material
import glimpse.materials.Plastic
import glimpse.materials.Textured
import glimpse.models.*
import glimpse.textures.*
import java.util.*

object Context {

	val appName = "APP_NAME"
	val appVersion = "APP_VERSION"

	val properties = Context.properties("/app.properties")

	val title = "${properties[appName]} v.${properties[appVersion]}"
}

fun main(args: Array<String>) {

	var aspect: Float = 1.333f

	val camera = camera {
		targeted {
			position { Vector(5f, 75.degrees, 0.degrees).toPoint() }
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
	}

	var model = transform(sphere(16))

	val textures = mutableMapOf<Textured.TextureType, Texture>()

	val texturedMaterial = Textured { textureType -> textures[textureType]!! }
	val plasticMaterial = Plastic(Color.WHITE)

	var material: Material = plasticMaterial

	var lights = listOf<Light>(
			directionLight {
				direction { Vector(-1f, 0f, 0f) }
			})

	glimpseFrame(Context.title) {
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
				menuItem("Load OBJ…") {
					onClick {
						openOBJFile { objFile ->
							model = transform(objFile.loadObjMeshes().firstOrNull() ?: mesh {})
						}
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
				menuItem("Single white") {
					onClick {
						lights = listOf(
								directionLight{
									direction { Vector(-1f, 0f, 0f) }
								})
					}
				}
				menuItem("Direction light") {
					onClick {
						lights = listOf(
								directionLight{
									direction { Vector(0f, 0f, -1f) }
									color { Color.RED }
								},
								directionLight{
									direction { Vector(-1f, 1f, 0f) }
									color { Color.GREEN }
								},
								directionLight{
									direction { Vector(-1f, -1f, 0f) }
									color { Color.BLUE }
								})
					}
				}
				menuItem("Point light") {
					onClick {
						lights = listOf(
								pointLight{
									position { Point(0f, 0f, 6f) }
									distance { 20f }
									color { Color.MAGENTA }
								},
								pointLight{
									position { Point(12f, -12f, 0f) }
									distance { 20f }
									color { Color.YELLOW }
								},
								pointLight{
									position { Point(2f, 2f, 0f) }
									distance { 20f }
									color { Color.CYAN }
								})
					}
				}
				menuItem("Spotlight") {
					onClick {
						lights = listOf(
								spotlight{
									position { Vector(5f, 23.5.degrees, Angle.RIGHT).toPoint() }
									target { Point.ORIGIN }
									angle { 20.degrees }
									distance { 100f }
									color { Color.RED }
								},
								spotlight{
									position { Point(5f, -5f, 5f) }
									target { Point.ORIGIN }
									angle { 10.degrees }
									distance { 100f }
									color { Color.GREEN }
								},
								spotlight{
									position { Point(5f, 5f, -5f) }
									target { Point.ORIGIN }
									angle { 180.degrees }
									distance { 100f }
									color { Color.BLUE }
								})
					}
				}
			}
			menu("Textures") {
				Textured.TextureType.values().forEach { textureType ->
					menuItem("${textureType.name.toLowerCase().capitalize()}…") {
						onClick {
							openImageFile { textureFile ->
								runInGLESContext {
									textures[textureType] = textureFile.inputStream().loadTexture { textureFile.name with mipmap }
								}
							}
						}
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
			textures[Textured.TextureType.AMBIENT] = Context.resource("ambient.png").loadTexture { withMipmap() }
			textures[Textured.TextureType.SPECULAR] = Context.resource("specular.png").loadTexture { withMipmap() }
			textures[Textured.TextureType.DIFFUSE] = Context.resource("diffuse.png").loadTexture { withMipmap() }
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
