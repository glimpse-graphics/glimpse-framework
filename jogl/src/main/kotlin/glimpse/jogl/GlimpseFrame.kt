package glimpse.jogl

import com.jogamp.opengl.util.FPSAnimator
import glimpse.gles.GLES
import glimpse.gles.Viewport
import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GLEventListener
import javax.media.opengl.awt.GLJPanel
import javax.swing.JFrame

class GlimpseFrame(title: String = "",  width: Int = 640, height: Int = 480, fps: Int = 30) : JFrame(title), GLEventListener {

	private var gles: GLES? = null

	private var init: GLES.() -> Unit = {}
	private var reshape: GLES.(viewport: Viewport) -> Unit = {}
	private var display: GLES.() -> Unit = {}
	private var dispose: GLES.() -> Unit = {}

	private val canvas = GLJPanel()
	private val animator = FPSAnimator(canvas, fps)

	init {
		contentPane.add(canvas)
		canvas.addGLEventListener(this)
		setSize(width, height)
		setLocationRelativeTo(null)
		onClose {
			animator.stop()
			dispose()
			System.exit(0)
		}
	}

	fun start() {
		isVisible = true
		animator.start()
	}

	fun onInit(init: GLES.() -> Unit) {
		this.init = init
	}

	fun onReshape(reshape: GLES.(viewport: Viewport) -> Unit) {
		this.reshape = reshape
	}

	fun onDisplay(display: GLES.() -> Unit) {
		this.display = display
	}

	fun onDispose(dispose: GLES.() -> Unit) {
		this.dispose = dispose
	}

	override fun init(drawable: GLAutoDrawable?) {
		requireNotNull(drawable)
		requireNotNull(drawable!!.gl)
		require(drawable.gl.isGL2ES2)
		requireNotNull(drawable.gl.gL2ES2)
		gles = glimpse.jogl.gles.GLES(drawable.gl.gL2ES2)
		gles?.init()
	}

	override fun reshape(drawable: GLAutoDrawable?, x: Int, y: Int, width: Int, height: Int) {
		gles?.reshape(Viewport(width, height))
	}

	override fun display(drawable: GLAutoDrawable?) {
		gles?.display()
	}

	override fun dispose(drawable: GLAutoDrawable?) {
		gles?.dispose()
	}
}
