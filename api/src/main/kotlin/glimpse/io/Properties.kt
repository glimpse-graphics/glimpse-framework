package glimpse.io

import java.util.*

/**
 * Returns properties loaded from a resource file.
 */
fun Any.properties(resourceName: String): Properties {
	val properties = Properties()
	properties.load(resource(resourceName).inputStream)
	return properties
}
