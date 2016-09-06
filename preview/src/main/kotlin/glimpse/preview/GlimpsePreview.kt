package glimpse.preview

import glimpse.jogl.*

fun main(args: Array<String>) {
	glimpseFrame("Glimpse Framework Preview", GlimpsePreviewEventListener()) {
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
		}
	}
}
