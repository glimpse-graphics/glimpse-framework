# GlimpseFramework Release Notes

## Version 0.4

* **Release date:** Oct 25, 2016
* **Git tag:** glimpse-framework-0.4

### New features

* Issue #38 – Properties of lights are lambdas.
* Issue #39 – It is now possible to dispose and reinitialize materials.
* Issue #40 – Added `loadObjMeshes` extension method to `List<String>`, `InputStream`, and `File`.
* Loading properties from resource file.
* JOGL implementation: File choosers for OBJ files and textures.
* JOGL implementation: Added actions running in GLES context.

### Other changes

* Issue #44 – Refactoring (changed API):
  * `readTexture` renamed to `loadTexture`,
  * `loadObjMesh` renamed to `loadObjMeshes`.
* Improved tests logging in build.
* Launch4j configuration for preview application.

## Version 0.3

* **Release date:** Sep 25, 2016
* **Git tag:** glimpse-framework-0.3

### New features

* Issue #14 – OBJ format support.
* Issue #15 – Lights support:
  * directional lights,
  * point lights,
  * spotlights.
* More camera features:
  * Issue #22 – orthographic camera projection,
  * Issue #23 – free-form camera position.
* Issue #24 – Removed uncomfortable `init` and `dispose` calls for materials.
* JOGL implementation: Getting current mouse position.

### Fixed bugs

* Issue #28 – Direct buffers were created once per frame, causing dramatic FPS loss.
* Issue #33 – Transformation of a `Model` was passed by value (not by name), when transformed again.
* Minor fixes in transformations.

### Other changes

* Updated dependencies

## Version 0.2

* **Release date:** Sep 15, 2016
* **Git tag:** glimpse-framework-0.2

First Kotlin release.

## Version 0.1

* **Release date:** Dec 23, 2015
* **Git tag:** glimpse-framework-0.1

Initial release.
