package glimpse.materials

import glimpse.Point
import glimpse.Vector
import glimpse.cos
import glimpse.lights.Light

/**
 * Common superclass for materials.
 */
abstract class AbstractMaterial : Material {

	protected operator fun ShaderHelper.set(name: String, lights: List<Light>) {
		this["${name}sCount"] = lights.size
		this["${name}Type"] = lights.map { light -> light.type }.toIntArray()
		this.setColors("${name}Color", lights.map { light -> light.color })
		this.setVectors("${name}Direction", lights.map { light ->
			when(light) {
				is Light.DirectionLight -> light.direction
				is Light.Spotlight -> light.position to light.target
				else -> Vector.NULL
			}
		})
		this.setPoints("${name}Position", lights.map { light ->
			when(light) {
				is Light.OmniLight -> light.position
				is Light.Spotlight -> light.position
				else -> Point.ORIGIN
			}
		})
		this["${name}Distance"] = lights.map { light ->
			when(light) {
				is Light.OmniLight -> light.distance
				is Light.Spotlight -> light.distance
				else -> Float.MAX_VALUE
			}
		}.toFloatArray()
		this["${name}AngleCos"] = lights.map { light ->
			when(light) {
				is Light.Spotlight -> cos(light.angle * .5f)
				else -> 0f
			}
		}.toFloatArray()
	}
}