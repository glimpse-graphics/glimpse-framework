package glimpse.lights

import glimpse.*

/**
 * Light source builder.
 */
sealed class LightBuilder {

	protected var color: () -> Color = { Color.WHITE }

	internal abstract fun build(): Light

	/**
	 * Sets color lambda.
	 */
	fun color(color: () -> Color) {
		this.color = color
	}

	/**
	 * Direction light source builder.
	 */
	class DirectionLightBuilder : LightBuilder() {

		private var direction: () -> Vector = { -Vector.Z_UNIT }

		/**
		 * Sets light direction lambda.
		 */
		fun direction(direction: () -> Vector) {
			this.direction = direction
		}

		override fun build() = Light.DirectionLight(direction, color)
	}

	/**
	 * Point light source builder.
	 */
	class PointLightBuilder : LightBuilder() {

		private var position: () -> Point = { Point.ORIGIN }
		private var distance: () -> Float = { 100f }

		/**
		 * Sets light position lambda.
		 */
		fun position(position: () -> Point) {
			this.position = position
		}

		/**
		 * Sets light maximum distance lambda.
		 */
		fun distance(distance: () -> Float) {
			this.distance = distance
		}

		override fun build() = Light.PointLight(position, distance, color)
	}

	/**
	 * Spotlight source builder.
	 */
	class SpotlightBuilder : LightBuilder() {

		private var position: () -> Point = { Vector.Z_UNIT.toPoint() }
		private var target: () -> Point = { Point.ORIGIN }
		private var angle: () -> Angle = { 60.degrees }
		private var distance: () -> Float = { 100f }

		/**
		 * Sets light position lambda.
		 */
		fun position(position: () -> Point) {
			this.position = position
		}

		/**
		 * Sets light cone target lambda.
		 */
		fun target(target: () -> Point) {
			this.target = target
		}

		/**
		 * Sets light cone angle lambda.
		 */
		fun angle(angle: () -> Angle) {
			this.angle = angle
		}

		/**
		 * Sets light maximum distance lambda.
		 */
		fun distance(distance: () -> Float) {
			this.distance = distance
		}

		override fun build() = Light.Spotlight(position, target, angle, distance, color)
	}
}

/**
 * Builds a direction light source initialized with an [init] function.
 */
fun directionLight(init: LightBuilder.DirectionLightBuilder.() -> Unit): Light.DirectionLight {
	val builder = LightBuilder.DirectionLightBuilder()
	builder.init()
	return builder.build()
}

/**
 * Builds a point light source initialized with an [init] function.
 */
fun pointLight(init: LightBuilder.PointLightBuilder.() -> Unit): Light.PointLight {
	val builder = LightBuilder.PointLightBuilder()
	builder.init()
	return builder.build()
}

/**
 * Builds a spotlight source initialized with an [init] function.
 */
fun spotlight(init: LightBuilder.SpotlightBuilder.() -> Unit): Light.Spotlight {
	val builder = LightBuilder.SpotlightBuilder()
	builder.init()
	return builder.build()
}
