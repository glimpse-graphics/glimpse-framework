# GlimpseFramework Release Notes

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
