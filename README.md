[![Build Status](https://travis-ci.org/GlimpseFramework/glimpse-framework.svg?branch=master)](https://travis-ci.org/GlimpseFramework/glimpse-framework)
[![Code Coverage](https://codecov.io/github/GlimpseFramework/glimpse-framework/coverage.svg?branch=master)](https://codecov.io/github/GlimpseFramework/glimpse-framework?branch=master)
[![Download](https://api.bintray.com/packages/glimpse-framework/org.glimpseframework/glimpse-framework/images/download.svg) ](https://bintray.com/glimpse-framework/org.glimpseframework/glimpse-framework/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

[![Stories Ready](https://badge.waffle.io/GlimpseFramework/glimpse-framework.svg?label=open&title=Open)](http://waffle.io/GlimpseFramework/glimpse-framework)
[![Stories In Progress](https://badge.waffle.io/GlimpseFramework/glimpse-framework.svg?label=in progress&title=In Progress)](http://waffle.io/GlimpseFramework/glimpse-framework)

# GlimpseFramework

OpenGL made simple

## Key features

* points, vectors, matrices
* models and model builders
* colors
* textures
* materials
* shaders
* cameras
* lights – planned
* animations – planned
* Blender OBJ file support – planned
* **Android support** – planned

## Example

### JOGL – desktop

```kotlin
fun main(args: Array<String>) {

	var aspect: Float = 1.333f

	val camera = camera {
		targeted {
			position {
				val time = (Date().time / 30L) % 360L
				Vector(10f, 60.degrees, time.degrees).toPoint()
			}
			target { Point.ORIGIN }
			up { Vector.Z_UNIT }
		}
		perspective {
			fov { 120.degrees }
			aspect { aspect }
			distanceRange(1f to 20f)
		}
	}

	val model = sphere(20, 30).transform {}

	val material = Plastic(Color.RED)

	glimpseFrame("Glimpse Framework") {
		onInit {
			Plastic.init(this)
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
			aspect = viewport.aspect
		}
		onRender {
			clearColorBuffer()
			clearDepthBuffer()
			material.render(model, camera)
		}
		onDispose {
			material.dispose()
		}
	}
}
```

### Android

Stay tuned…
