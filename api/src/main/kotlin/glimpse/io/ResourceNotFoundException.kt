package glimpse.io

import glimpse.GlimpseException

/**
 * Resource not found exception.
 *
 * @param resource Resource.
 */
class ResourceNotFoundException(resource: Resource) : GlimpseException("${resource.context.`package`.name}.${resource.name}")
