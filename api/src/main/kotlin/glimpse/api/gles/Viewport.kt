package glimpse.api.gles

data class Viewport(val width: Int, val height: Int) {
	override fun toString(): String = "$width×$height"
}
