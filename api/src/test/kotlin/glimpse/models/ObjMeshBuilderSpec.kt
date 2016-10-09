package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector
import glimpse.io.resource
import glimpse.test.GlimpseSpec
import io.kotlintest.matchers.be

class ObjMeshBuilderSpec : GlimpseSpec() {

	init {

		"Single triangle built from OBJ file" should {
			"be a single instance of mesh" {
				val result = loadObjTriangle()
				result should haveSize(1)
				result[0] should be a Mesh::class
			}
			"have a single face" {
				val result = loadObjTriangle()
				result[0].faces should haveSize(1)
			}
			"have correct positions" {
				val result = loadObjTriangle()
				result[0].positions shouldBe listOf(Point(1f, 1f, 1f), Point(1f, 1f, -1f), Point(1f, -1f, 1f))
			}
			"have correct texture coordinates" {
				val result = loadObjTriangle()
				result[0].textureCoordinates shouldBe listOf(TextureCoordinates.BOTTOM_LEFT, TextureCoordinates.BOTTOM_RIGHT, TextureCoordinates.TOP_LEFT)
			}
			"have correct normals" {
				val result = loadObjTriangle()
				result[0].normals shouldBe listOf(Vector.X_UNIT, Vector.Y_UNIT, Vector.Z_UNIT)
			}
		}

		"Single triangle with no textures built from OBJ file" should {
			"be a single instance of mesh" {
				val result = loadObjTriangleNoTextures()
				result should haveSize(1)
				result[0] should be a Mesh::class
			}
			"have a single face" {
				val result = loadObjTriangleNoTextures()
				result[0].faces should haveSize(1)
			}
			"have correct positions" {
				val result = loadObjTriangleNoTextures()
				result[0].positions shouldBe listOf(Point(1f, 1f, 1f), Point(1f, 1f, -1f), Point(1f, -1f, 1f))
			}
			"always have (0, 0) texture coordinates" {
				val result = loadObjTriangleNoTextures()
				result[0].textureCoordinates shouldBe listOf(TextureCoordinates.BOTTOM_LEFT, TextureCoordinates.BOTTOM_LEFT, TextureCoordinates.BOTTOM_LEFT)
			}
			"have correct normals" {
				val result = loadObjTriangleNoTextures()
				result[0].normals shouldBe listOf(Vector.X_UNIT, Vector.Y_UNIT, Vector.Z_UNIT)
			}
		}

		"Two triangles built from OBJ file" should {
			"be two separate instances of mesh" {
				val result = loadObjTwoTriangles()
				result should haveSize(2)
				result[0] should be a Mesh::class
				result[1] should be a Mesh::class
			}
			"both have a single face" {
				val result = loadObjTwoTriangles()
				result[0].faces should haveSize(1)
				result[1].faces should haveSize(1)
			}
			"both have correct positions" {
				val result = loadObjTwoTriangles()
				result[0].positions shouldBe listOf(Point(1f, 1f, 1f), Point(1f, 1f, -1f), Point(1f, -1f, 1f))
				result[1].positions shouldBe listOf(Point(-1f, 1f, 1f), Point(-1f, 1f, -1f), Point(-1f, -1f, 1f))
			}
			"both have correct texture coordinates" {
				val result = loadObjTwoTriangles()
				result[0].textureCoordinates shouldBe listOf(TextureCoordinates.BOTTOM_LEFT, TextureCoordinates.BOTTOM_RIGHT, TextureCoordinates.TOP_LEFT)
				result[1].textureCoordinates shouldBe listOf(TextureCoordinates.BOTTOM_RIGHT, TextureCoordinates.BOTTOM_LEFT, TextureCoordinates.TOP_RIGHT)
			}
			"both have correct normals" {
				val result = loadObjTwoTriangles()
				result[0].normals shouldBe listOf(Vector.X_UNIT, Vector.Y_UNIT, Vector.Z_UNIT)
				result[1].normals shouldBe listOf(Vector(-1f, 0f, 0f), Vector(0f, -1f, 0f), Vector(0f, 0f, -1f))
			}
		}

	}

	private fun loadObjTriangle() = resource("triangle.obj").loadObjMeshes()
	private fun loadObjTriangleNoTextures() = resource("triangle_no_textures.obj").loadObjMeshes()
	private fun loadObjTwoTriangles() = resource("two_triangles.obj").loadObjMeshes()
}
