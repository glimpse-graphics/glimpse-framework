package glimpse.jogl

import java.awt.event.ActionEvent
import javax.swing.*

/**
 * Builds a [JMenuBar] initialized with an [init] function.
 */
fun JFrame.menuBar(init: JMenuBar.() -> Unit) {
	val menuBar = JMenuBar()
	menuBar.init()
	setJMenuBar(menuBar)
}

/**
 * Builds a [JMenu] initialized with an [init] function.
 */
fun JMenuBar.menu(caption: String, init: JMenu.() -> Unit) {
	val menu = JMenu(caption)
	menu.init()
	add(menu)
}

/**
 * Builds a [JMenu] initialized with an [init] function.
 */
fun JMenu.menu(caption: String, init: JMenu.() -> Unit) {
	val menu = JMenu(caption)
	menu.init()
	add(menu)
}

/**
 * Builds a [JMenuItem] initialized with an [init] function.
 */
fun JMenu.menuItem(caption: String, init: JMenuItem.() -> Unit) {
	val menuItem = JMenuItem(caption)
	menuItem.init()
	add(menuItem)
}

/**
 * Adds a separator to a [JMenu].
 */
fun JMenu.separator() {
	addSeparator()
}

/**
 * Sets an action to be executed when the [JMenuItem] is selected.
 */
fun JMenuItem.onClick(callback: () -> Unit) {
	action = MenuItemAction(text, callback)
}

private class MenuItemAction(caption: String, val callback: () -> Unit) : AbstractAction(caption) {
	override fun actionPerformed(e: ActionEvent?) {
		callback()
	}
}