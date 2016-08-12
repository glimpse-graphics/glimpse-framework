package glimpse.api

/**
 * A three-dimensional model.
 *
 * @param mesh Mesh defining model's surface.
 * @param modelMatrix Model transformation matrix.
 */
class Model(val mesh: Mesh, val modelMatrix: Matrix)
