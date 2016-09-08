[![Build Status](https://travis-ci.org/GlimpseFramework/glimpse-framework.svg?branch=master)](https://travis-ci.org/GlimpseFramework/glimpse-framework)
[![Code Coverage](https://codecov.io/github/GlimpseFramework/glimpse-framework/coverage.svg?branch=master)](https://codecov.io/github/GlimpseFramework/glimpse-framework?branch=master)
<!-- [![Download](https://api.bintray.com/packages/glimpse-framework/org.glimpseframework/glimpse-framework/images/download.svg) ](https://bintray.com/glimpse-framework/org.glimpseframework/glimpse-framework/_latestVersion) -->
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

[![Stories Ready](https://badge.waffle.io/GlimpseFramework/glimpse-framework.svg?label=open&title=Open)](http://waffle.io/GlimpseFramework/glimpse-framework)
[![Stories In Progress](https://badge.waffle.io/GlimpseFramework/glimpse-framework.svg?label=in progress&title=In Progress)](http://waffle.io/GlimpseFramework/glimpse-framework)

# GlimpseFramework

OpenGL made easy

## Key features

* points, vectors, matrices
* models and model builders
* colors
* textures – planned
* materials – planned
* shaders
* cameras – planned
* lights – planned
* animations – planned
* Blender OBJ file support – planned

## Example

```kotlin
fun main(args: Array<String>) {
	var projectionMatrix = perspectiveProjectionMatrix(90.degrees, 1f, 1f, 20f)
	val viewMatrix = lookAtViewMatrix(Point(10f, 0f, 5f), Point.ORIGIN, Vector.Z_UNIT)
	val mesh = sphere(16)

	glimpseFrame("Glimpse Framework Preview") {
		onInit {
			PlainShaderProgram(this)
			clearColor = Color.BLACK
			clearDepth = 1f
			isDepthTest = true
			depthTestFunction = DepthTestFunction.LESS_OR_EQUAL
			isBlend = true
			blendFunction = BlendFactor.SRC_ALPHA to BlendFactor.ONE_MINUS_SRC_ALPHA
			isCullFace = false
		}
		onResize { v ->
			viewport = v
			val aspect = viewport.width.toFloat() / viewport.height.toFloat()
			projectionMatrix = perspectiveProjectionMatrix(90.degrees, aspect, 1f, 20f)
		}
		onRender {
			clearColorBuffer()
			clearDepthBuffer()
			PlainShaderProgram.mvpMatrix { projectionMatrix * viewMatrix }
			PlainShaderProgram.drawMesh { mesh }
		}
		onDispose {
			PlainShaderProgram.dispose()
		}
	}
}
```
