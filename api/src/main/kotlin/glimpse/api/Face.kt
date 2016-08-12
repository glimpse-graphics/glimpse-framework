package glimpse.api

/**
 * A triangular face of a three-dimensional model.
 *
 * @param v1 First vertex.
 * @param v2 Second vertex.
 * @param v3 Third vertex.
 */
data class Face(val v1: Vertex, val v2: Vertex, val v3: Vertex)
