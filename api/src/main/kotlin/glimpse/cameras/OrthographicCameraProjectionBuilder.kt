package glimpse.cameras

/**
 * Builder of an [OrthographicCameraProjection].
 */
class OrthographicCameraProjectionBuilder {

	private var height: () -> Float = { 2f }
	private var aspect: () -> Float = { 1f }
	private var near: Float = 1f
	private var far: Float = 100f

	/**
	 * Sets camera projected region height lambda.
	 */
	fun height(height: () -> Float) {
		this.height = height
	}

	/**
	 * Sets camera aspect ratio lambda.
	 */
	fun aspect(aspect: () -> Float) {
		this.aspect = aspect
	}

	/**
	 * Sets camera near and far clipping planes.
	 */
	fun distanceRange(range: Pair<Float, Float>) {
		near = range.first
		far = range.second
	}

	internal fun build(): OrthographicCameraProjection = OrthographicCameraProjection(height, aspect, near, far)
}

/**
 * Builds orthographic camera projecion initialized with an [init] function.
 */
fun CameraBuilder.orthographic(init: OrthographicCameraProjectionBuilder.() -> Unit) {
	val cameraProjectionBuilder = OrthographicCameraProjectionBuilder()
	cameraProjectionBuilder.init()
	cameraProjection = cameraProjectionBuilder.build()
}
