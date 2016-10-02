package glimpse.jogl

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.awt.GLJPanel
import com.jogamp.opengl.util.FPSAnimator
import glimpse.gles.Disposables
import glimpse.gles.GLES
import glimpse.gles.Viewport
import glimpse.gles.delegates.GLESDelegate
import java.util.concurrent.BlockingQueue
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

	private val gles: GLES by GLESDelegate

	private var init: GLES.() -> Unit = {}
	private var reshape: GLES.(viewport: Viewport) -> Unit = {}
	private var display: GLES.() -> Unit = {}
	private var dispose: GLES.() -> Unit = {}

	private val canvas = GLJPanel()
	private val animator = FPSAnimator(canvas, fps)

	private val eventListener = EventListener()

	private val actions: BlockingQueue<GLES.() -> Unit> =
			java.util.concurrent.LinkedBlockingQueue()

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
	 */
	fun onResize(reshape: GLES.(Viewport) -> Unit) {
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

	/**
	 * Enqueues an [action] to be run in GLES context.
	 */
	fun runInGLESContext(action: GLES.() -> Unit) {
		actions.put(action)
	}

	private inner class EventListener : GLEventListener {

		override fun init(drawable: GLAutoDrawable?) {
			require(drawable!!.gl.isGL2ES2) { "OpenGL does not conform to GL2ES2 profile." }
			GLESDelegate(glimpse.jogl.gles.GLES(drawable.gl.gL2ES2))
			gles.init()
			runActions()
		}

		override fun reshape(drawable: GLAutoDrawable?, x: Int, y: Int, width: Int, height: Int) {
			gles.reshape(Viewport(width, height))
		}

		override fun display(drawable: GLAutoDrawable?) {
			runActions()
			gles.display()
		}

		override fun dispose(drawable: GLAutoDrawable?) {
			gles.dispose()
			Disposables.disposeAll()
		}

		private fun runActions() {
			while (!actions.isEmpty()) {
				actions.poll().invoke(gles)
			}
		}
	}
}
