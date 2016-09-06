package glimpse.jogl

import com.jogamp.opengl.util.FPSAnimator
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.media.opengl.GLEventListener
import javax.media.opengl.awt.GLJPanel
import javax.swing.JFrame

fun glimpseFrame(title: String, listener: GLEventListener, width: Int = 640, height: Int = 480, fps: Int = 30, init: JFrame.() -> Unit) {
	frame(title, width, height) {
		val canvas = GLJPanel()
		val animator = FPSAnimator(canvas, fps)
		onClose {
			animator.stop()
			dispose()
			System.exit(0)
		}
		contentPane.add(canvas)
		canvas.addGLEventListener(listener)
		init()
		animator.start()
	}
}

fun frame(title: String, width: Int = 640, height: Int = 480, init: JFrame.() -> Unit) {
	val frame = JFrame(title)
	frame.setSize(width, height)
	frame.setLocationRelativeTo(null)
	frame.init()
	frame.isVisible = true
}

fun JFrame.onClose(callback: () -> Unit) {
	addWindowListener(OnCloseWindowAdapter(callback))
}

class OnCloseWindowAdapter(val callback: () -> Unit) : WindowAdapter() {
	override fun windowClosing(event: WindowEvent?) {
		callback()
	}
}
