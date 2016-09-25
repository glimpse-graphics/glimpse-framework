package glimpse.buffers

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

internal fun directByteBuffer(size: Int): ByteBuffer =
		ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())

internal fun directFloatBuffer(size: Int): FloatBuffer =
		directByteBuffer(size * 4).asFloatBuffer()

internal fun <T> List<T>.toDirectFloatBuffer(size: Int, transform: (T) -> Array<Float>): FloatBuffer =
		directFloatBuffer(size).put(fold(emptyList<Float>()) { list, next -> list + transform(next) }.toFloatArray())

internal fun FloatBuffer.toList(): List<Float> {
	rewind()
	val array = FloatArray(capacity())
	get(array)
	return array.toList()
}
