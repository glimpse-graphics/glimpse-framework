package glimpse.models

/**
 * Polygon.
 *
 * @property curve Boundary of the polygon.
 * @property faces Faces adding up to the interior of the polygon, defined as a list of triples of points indices.
 */
class Polygon(val curve: Curve, val faces: List<Triple<Int, Int, Int>>)
