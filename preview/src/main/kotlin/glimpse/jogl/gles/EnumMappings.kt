package glimpse.jogl.gles

import glimpse.api.gles.*
import javax.media.opengl.GL2ES2

internal val depthTestFunctionMapping = mapOf(
		DepthTestFunction.NEVER to GL2ES2.GL_NEVER,
		DepthTestFunction.LESS to GL2ES2.GL_LESS,
		DepthTestFunction.EQUAL to GL2ES2.GL_EQUAL,
		DepthTestFunction.LESS_OR_EQUAL to GL2ES2.GL_LEQUAL,
		DepthTestFunction.GREATER to GL2ES2.GL_GREATER,
		DepthTestFunction.NOT_EQUAL to GL2ES2.GL_NOTEQUAL,
		DepthTestFunction.GREATER_OR_EQUAL to GL2ES2.GL_GEQUAL,
		DepthTestFunction.ALWAYS to GL2ES2.GL_ALWAYS)

internal val blendFactorMapping = mapOf(
		BlendFactor.ZERO to GL2ES2.GL_ZERO,
		BlendFactor.ONE to GL2ES2.GL_ONE,
		BlendFactor.SRC_COLOR to GL2ES2.GL_SRC_COLOR,
		BlendFactor.ONE_MINUS_SRC_COLOR to GL2ES2.GL_ONE_MINUS_SRC_COLOR,
		BlendFactor.DST_COLOR to GL2ES2.GL_DST_COLOR,
		BlendFactor.ONE_MINUS_DST_COLOR to GL2ES2.GL_ONE_MINUS_DST_COLOR,
		BlendFactor.SRC_ALPHA to GL2ES2.GL_SRC_ALPHA,
		BlendFactor.ONE_MINUS_SRC_ALPHA to GL2ES2.GL_ONE_MINUS_SRC_ALPHA,
		BlendFactor.DST_ALPHA to GL2ES2.GL_DST_ALPHA,
		BlendFactor.ONE_MINUS_DST_ALPHA to GL2ES2.GL_ONE_MINUS_DST_ALPHA,
		BlendFactor.CONSTANT_COLOR to GL2ES2.GL_CONSTANT_COLOR,
		BlendFactor.ONE_MINUS_CONSTANT_COLOR to GL2ES2.GL_ONE_MINUS_CONSTANT_COLOR,
		BlendFactor.CONSTANT_ALPHA to GL2ES2.GL_CONSTANT_ALPHA,
		BlendFactor.ONE_MINUS_CONSTANT_ALPHA to GL2ES2.GL_ONE_MINUS_CONSTANT_ALPHA)

internal val cullFaceModeMapping = mapOf(
		CullFaceMode.BACK to GL2ES2.GL_BACK,
		CullFaceMode.FRONT to GL2ES2.GL_FRONT,
		CullFaceMode.FRONT_AND_BACK to GL2ES2.GL_FRONT_AND_BACK)

internal val textureMinificationFilterMapping = mapOf(
		TextureMinificationFilter.NEAREST to GL2ES2.GL_NEAREST,
		TextureMinificationFilter.LINEAR to GL2ES2.GL_LINEAR,
		TextureMinificationFilter.NEAREST_MIPMAP_NEAREST to GL2ES2.GL_NEAREST_MIPMAP_NEAREST,
		TextureMinificationFilter.LINEAR_MIPMAP_NEAREST to GL2ES2.GL_LINEAR_MIPMAP_NEAREST,
		TextureMinificationFilter.NEAREST_MIPMAP_LINEAR to GL2ES2.GL_NEAREST_MIPMAP_LINEAR,
		TextureMinificationFilter.LINEAR_MIPMAP_LINEAR to GL2ES2.GL_LINEAR_MIPMAP_LINEAR)

internal val textureMagnificationFilterMapping = mapOf(
		TextureMagnificationFilter.NEAREST to GL2ES2.GL_NEAREST,
		TextureMagnificationFilter.LINEAR to GL2ES2.GL_LINEAR)

internal val textureWrappingMapping = mapOf(
		TextureWrapping.REPEAT to GL2ES2.GL_REPEAT,
		TextureWrapping.MIRRORED_REPEAT to GL2ES2.GL_MIRRORED_REPEAT,
		TextureWrapping.CLAMP_TO_EDGE to GL2ES2.GL_CLAMP_TO_EDGE)

internal val shaderTypeMapping = mapOf(
		ShaderType.VERTEX to GL2ES2.GL_VERTEX_SHADER,
		ShaderType.FRAGMENT to GL2ES2.GL_FRAGMENT_SHADER)
