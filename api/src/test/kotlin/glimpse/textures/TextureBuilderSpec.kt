package glimpse.textures

import com.nhaarman.mockito_kotlin.*
import glimpse.gles.GLES
import glimpse.io.resource
import glimpse.test.GlimpseSpec
import java.io.InputStream

class TextureBuilderSpec : GlimpseSpec() {

	init {

		"Texture builder" should {
			"create a texture in GLES implementation" {
				val glesMock = createGLESMock()
				val inputStreamMock = createInputStreamMock()
				val builder = TextureBuilder(glesMock)
				builder.build(inputStreamMock)
				verify(glesMock).generateTexture()
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).textureImage2D(inputStreamMock, "", false)
			}
			"create a texture object" {
				val glesMock = createGLESMock()
				val inputStreamMock = createInputStreamMock()
				val builder = TextureBuilder(glesMock)
				val texture = builder.build(inputStreamMock)
				texture.deleted shouldBe false
				texture.gles shouldBe glesMock
				texture.handle shouldBe TextureHandle(1)
			}
		}

		"Texture builder function for input stream" should {
			"create a texture with mipmap" {
				val glesMock = createGLESMock()
				createInputStreamMock().readTexture(glesMock) {
					name = "mipmapped.png" with mipmap
				}
				verify(glesMock).generateTexture()
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).textureImage2D(any(), eq("mipmapped.png"), eq(true))
			}
			"create a texture without mipmap" {
				val glesMock = createGLESMock()
				createInputStreamMock().readTexture(glesMock) {
					name = "linear.png" without mipmap
				}
				verify(glesMock).generateTexture()
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).textureImage2D(any(), eq("linear.png"), eq(false))
			}
			"create a texture object" {
				val glesMock = createGLESMock()
				val texture = createInputStreamMock().readTexture(glesMock) {
					name = "linear.png" without mipmap
				}
				texture.deleted shouldBe false
				texture.gles shouldBe glesMock
				texture.handle shouldBe TextureHandle(1)
			}
		}

		"Texture builder function for resource" should {
			"create a texture" {
				val glesMock = createGLESMock()
				resource("empty_file.png").readTexture(glesMock)
				verify(glesMock).generateTexture()
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).textureImage2D(any(), eq("empty_file.png"), any())
			}
		}

	}

	private fun createInputStreamMock(): InputStream = mock()

	private fun createGLESMock(): GLES = mock {
		on { generateTexture() } doReturn TextureHandle(1)
	}
}