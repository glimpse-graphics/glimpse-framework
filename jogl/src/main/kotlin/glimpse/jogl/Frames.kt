package glimpse.jogl

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

fun glimpseFrame(title: String, width: Int = 640, height: Int = 480, fps: Int = 30, init: GlimpseFrame.() -> Unit) {
	val frame = GlimpseFrame(title, width, height, fps)
	frame.init()
	frame.start()
}

fun JFrame.onClose(callback: () -> Unit) {
	addWindowListener(OnCloseWindowAdapter(callback))
}

class OnCloseWindowAdapter(val callback: () -> Unit) : WindowAdapter() {
	override fun windowClosing(event: WindowEvent?) {
		callback()
	}
}
