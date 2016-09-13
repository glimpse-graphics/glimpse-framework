package glimpse.io

import java.io.BufferedReader
import java.io.InputStream

/**
 * A resource file wrapper.
 *
 * @property context Class context of the resource.
 * @property name Resource name.
 */
data class Resource(val context: Class<out Any>, val name: String) {

	/**
	 * Newly opened resource input stream.
	 */
	val inputStream: InputStream get() = context.getResourceAsStream(name) ?: throw ResourceNotFoundException(this)

	/**
	 * Lines in the resource file.
	 */
	val lines: List<String> by lazy {
		tailrec fun readLines(reader: BufferedReader, lines: List<String>): List<String> {
			val line = reader.readLine()
			return if (line == null) lines else readLines(reader, lines + line)
		}
		val reader = inputStream.bufferedReader()
		val lines = readLines(context.getResourceAsStream(name).bufferedReader(), emptyList())
		reader.close()
		lines
	}
}

/**
 * Returns a resource with the given [name] in this object's context.
 */
fun Any.resource(name: String): Resource = Resource(javaClass, name)
