package glimpse.jogl

import glimpse.api.gles.GLES
import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GLEventListener
import javax.swing.JFrame

class GlimpseFrame(title: String) : JFrame(title), GLEventListener {

	private var init: GLES.() -> Unit = {}

	override fun init(drawable: GLAutoDrawable?) {
		throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun dispose(drawable: GLAutoDrawable?) {
		throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun reshape(drawable: GLAutoDrawable?, x: Int, y: Int, width: Int, height: Int) {
		throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun display(drawable: GLAutoDrawable?) {
		throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}
