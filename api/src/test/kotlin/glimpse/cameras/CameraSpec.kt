package glimpse.cameras

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import glimpse.Point
import glimpse.scalingMatrix
import glimpse.test.GlimpseSpec

class CameraSpec : GlimpseSpec() {

	init {

		"Camera" should {
			"properly combine projection and view matrices" {
				val cameraView = mock<CameraView> {
					on { viewMatrix } doReturn scalingMatrix(y = 2f)
				}
				val cameraProjection = mock<CameraProjection> {
					on { projectionMatrix } doReturn scalingMatrix(z = 3f)
				}
				val camera = Camera(cameraView, cameraProjection)
				camera.cameraMatrix shouldBeRoughly scalingMatrix(1f, 2f, 3f)
			}
			"properly return camera position" {
				val cameraView = mock<CameraView> {
					on { position } doReturn Point(1f, 2f, 3f)
				}
				val cameraProjection = mock<CameraProjection>()
				val camera = Camera(cameraView, cameraProjection)
				camera.position shouldBeRoughly Point(1f, 2f, 3f)
			}
		}

	}
}