package glimpse.test

import glimpse.*
import io.kotlintest.properties.Gen

fun Gen.Companion.chooseFloat(min: Int, max: Int) = object : Gen<Float> {
	val intGen = choose(min, max)
	override fun generate(): Float = intGen.generate().toFloat()
}

fun Gen.Companion.angle(floatGen: Gen<Float>) = object : Gen<Angle> {
	override fun generate(): Angle = floatGen.generate().degrees
}

fun Gen.Companion.point(floatGen: Gen<Float>) = object : Gen<Point> {
	override fun generate(): Point = Point(floatGen.generate(), floatGen.generate(), floatGen.generate())
}

fun Gen.Companion.vector(floatGen: Gen<Float>) = object : Gen<Vector> {
	override fun generate(): Vector = Vector(floatGen.generate(), floatGen.generate(), floatGen.generate())
}

fun Gen.Companion.matrix(floatGen: Gen<Float>) = object : Gen<Matrix> {
	override fun generate(): Matrix = Matrix((0..15).map { floatGen.generate() }.toList())
}
