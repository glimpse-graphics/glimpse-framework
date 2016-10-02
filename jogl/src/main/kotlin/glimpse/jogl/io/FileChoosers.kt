package glimpse.jogl.io

import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFrame

/**
 * Displays a Swing file chooser for opening a file.
 * @param fileFilter File filter.
 * @param onOpen Action to be run when the user chooses to open the file.
 * @param onCancel Action to be run when the user cancels.
 */
fun JFrame.openFile(fileFilter: PredicateFileFilter, onOpen: (File) -> Unit, onCancel: () -> Unit = {}) {
	val fileChooser = JFileChooser()
	fileChooser.addChoosableFileFilter(fileFilter)
	fileChooser.isAcceptAllFileFilterUsed = false
	when (fileChooser.showOpenDialog(this)) {
		JFileChooser.APPROVE_OPTION -> onOpen(fileChooser.selectedFile)
		else -> onCancel()
	}
}

/**
 * Displays a Swing file chooser for opening an OBJ file.
 * @param onOpen Action to be run when the user chooses to open the file.
 */
fun JFrame.openOBJFile(onOpen: (File) -> Unit): Unit = openFile(objFileFilter, onOpen)

/**
 * Displays a Swing file chooser for opening an image file.
 * @param onOpen Action to be run when the user chooses to open the file.
 */
fun JFrame.openImageFile(onOpen: (File) -> Unit): Unit = openFile(imageFileFilter, onOpen)
