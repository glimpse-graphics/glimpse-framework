package glimpse.jogl

import com.jogamp.opengl.util.FPSAnimator
import glimpse.gles.GLES
import glimpse.gles.Viewport
import java.awt.event.WindowEvent
import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GLEventListener
import javax.media.opengl.awt.GLJPanel
import javax.swing.JFrame

/**
 * Glimpse Framework [JFrame] implementation.
 *
 * @param title Frame title.
 * @param width Frame initial width.
 * @param height Frame initial height.
 * @param fps Frames per second animation rate.
 */
class GlimpseFrame(title: String = "",  width: Int = 640, height: Int = 480, fps: Int = 30) : JFrame(title) {

	private var gles: GLES? = null

	private var init: GLES.() -> Unit = {}
	private var reshape: GLES.(viewport: Viewport) -> Unit = {}
	private var display: GLES.() -> Unit = {}
	private var dispose: GLES.() -> Unit = {}

	private val canvas = GLJPanel()
	private val animator = FPSAnimator(canvas, fps)

	private val eventListener = EventListener()

	init {
		contentPane.add(canvas)
		canvas.addGLEventListener(eventListener)
		setSize(width, height)
		setLocationRelativeTo(null)
		onClose {
			animator.stop()
			dispose()
			System.exit(0)
		}
	}

	/**
	 * Starts GL animation.
	 */
	fun start() {
		isVisible = true
		animator.start()
	}

	/**
	 * GL initialization lambda.
	 */
	fun onInit(init: GLES.() -> Unit) {
		this.init = init
	}

	/**
	 * GL resize lambda.
	 *
	 * @param viewport Rendering viewport.
	 */
	fun onResize(reshape: GLES.(viewport: Viewport) -> Unit) {
		this.reshape = reshape
	}

	/**
	 * GL rendering lambda.
	 */
	fun onRender(display: GLES.() -> Unit) {
		this.display = display
	}

	/**
	 * GL dispose lambda.
	 */
	fun onDispose(dispose: GLES.() -> Unit) {
		this.dispose = dispose
	}

	private inner class EventListener : GLEventListener {

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
}
