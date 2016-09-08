package glimpse.buffers

import java.nio.ByteBuffer
import java.nio.ByteOrder

internal fun directByteBuffer(size: Int) =
		ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())

internal fun directFloatBuffer(size: Int) =
		directByteBuffer(size * 4).asFloatBuffer()

internal fun <T> List<T>.toDirectFloatBuffer(size: Int, transform: (T) -> Array<Float>) =
		directFloatBuffer(size).put(fold(emptyList<Float>()) { list, next -> list + transform(next) }.toFloatArray())