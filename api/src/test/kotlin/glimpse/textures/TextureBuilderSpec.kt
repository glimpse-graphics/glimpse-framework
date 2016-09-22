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
				val glesMock = glesMock()
				val inputStreamMock = createInputStreamMock()
				val builder = TextureBuilder()
				builder.build(inputStreamMock)
				verify(glesMock).generateTexture()
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).textureImage2D(inputStreamMock, "", false)
			}
			"create a texture object" {
				val glesMock = glesMock()
				val inputStreamMock = createInputStreamMock()
				val builder = TextureBuilder()
				val texture = builder.build(inputStreamMock)
				texture.deleted shouldBe false
				texture.gles shouldBe glesMock
				texture.handle shouldBe TextureHandle(1)
			}
		}

		"Texture builder function for input stream" should {
			"create a texture with mipmap" {
				val glesMock = glesMock()
				createInputStreamMock().readTexture {
					name = "mipmapped.png" with mipmap
				}
				verify(glesMock).generateTexture()
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).textureImage2D(any(), eq("mipmapped.png"), eq(true))
			}
			"create a texture without mipmap" {
				val glesMock = glesMock()
				createInputStreamMock().readTexture {
					name = "linear.png" without mipmap
				}
				verify(glesMock).generateTexture()
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).textureImage2D(any(), eq("linear.png"), eq(false))
			}
			"create a texture object" {
				val glesMock = glesMock()
				val texture = createInputStreamMock().readTexture {
					name = "linear.png" without mipmap
				}
				texture.deleted shouldBe false
				texture.gles shouldBe glesMock
				texture.handle shouldBe TextureHandle(1)
			}
		}

		"Texture builder function for resource" should {
			"create a texture" {
				val glesMock = glesMock()
				resource("empty_file.png").readTexture()
				verify(glesMock).generateTexture()
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).textureImage2D(any(), eq("empty_file.png"), any())
			}
		}

	}

	private fun createInputStreamMock(): InputStream = mock()

	private fun glesMock(): GLES = glesMock {
		on { generateTexture() } doReturn TextureHandle(1)
	}
}