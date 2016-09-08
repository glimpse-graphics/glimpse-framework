package glimpse

/**
 * Common superclass for all Glimpse Framework exceptions.
 *
 * @param message Exception message.
 */
abstract class GlimpseException(message: String = "Exception in Glimpse Framework") : Exception(message)
