package glimpse.jogl

import java.awt.event.ActionEvent
import javax.swing.*

fun JFrame.menuBar(init: JMenuBar.() -> Unit) {
	val menuBar = JMenuBar()
	menuBar.init()
	setJMenuBar(menuBar)
}

fun JMenuBar.menu(caption: String, init: JMenu.() -> Unit) {
	val menu = JMenu(caption)
	menu.init()
	add(menu)
}

fun JMenu.menu(caption: String, init: JMenu.() -> Unit) {
	val menu = JMenu(caption)
	menu.init()
	add(menu)
}

fun JMenu.menuItem(caption: String, init: JMenuItem.() -> Unit) {
	val menuItem = JMenuItem(caption)
	menuItem.init()
	add(menuItem)
}

fun JMenu.separator() {
	addSeparator()
}

fun JMenuItem.onClick(callback: () -> Unit) {
	action = MenuItemAction(text, callback)
}

class MenuItemAction(caption: String, val callback: () -> Unit) : AbstractAction(caption) {
	override fun actionPerformed(e: ActionEvent?) {
		callback()
	}
}