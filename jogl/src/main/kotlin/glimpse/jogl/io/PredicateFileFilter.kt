package glimpse.jogl.io

import java.io.File
import javax.swing.filechooser.FileFilter

/**
 * Predicate file filter for Swing file chooser.
 *
 * @property name Filter name that will be displayed in file chooser.
 * @property predicate File display predicate.
 */
class PredicateFileFilter(val name: String, val predicate: File.() -> Boolean) : FileFilter() {

	/**
	 * Returns `true` if the [file] should be displayed in file chooser.
	 */
	override fun accept(file: File?): Boolean = file?.shouldBeDisplayed() ?: false

	private fun File.shouldBeDisplayed(): Boolean = isDirectory || predicate()

	/**
	 * Returns filter description displayed in file chooser.
	 */
	override fun getDescription(): String = name
}
