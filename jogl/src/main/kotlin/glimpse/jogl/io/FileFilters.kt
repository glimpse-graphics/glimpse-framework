package glimpse.jogl.io

/**
 * OBJ file filter.
 */
val objFileFilter = PredicateFileFilter("OBJ files") {
	"obj".equals(extension, true)
}

/**
 * Image file filter.
 */
val imageFileFilter = PredicateFileFilter("Image files") {
	extension.toLowerCase() in listOf("bmp", "jpg", "jpeg", "png")
}
