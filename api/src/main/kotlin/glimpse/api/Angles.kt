package glimpse.api

/**
 * Creates [Angle] from degrees.
 */
val Int.degrees: Angle get() = this.toFloat().degrees

/**
 * Creates [Angle] from radians.
 */
val Int.radians: Angle get() = this.toFloat().radians

/**
 * Creates [Angle] from degrees.
 */
val Long.degrees: Angle get() = this.toFloat().degrees

/**
 * Creates [Angle] from radians.
 */
val Long.radians: Angle get() = this.toFloat().radians

/**
 * Creates [Angle] from degrees.
 */
val Float.degrees: Angle get() = Angle.fromDeg(this)

/**
 * Creates [Angle] from radians.
 */
val Float.radians: Angle get() = Angle.fromRad(this)

/**
 * Creates [Angle] from degrees.
 */
val Double.degrees: Angle get() = this.toFloat().degrees

/**
 * Creates [Angle] from radians.
 */
val Double.radians: Angle get() = this.toFloat().radians


/**
 * Returns the trigonometric sine of an [angle].
 */
fun sin(angle: Angle) = Math.sin(angle.rad.toDouble()).toFloat()

/**
 * Returns the trigonometric cosine of an [angle].
 */
fun cos(angle: Angle) = Math.cos(angle.rad.toDouble()).toFloat()

/**
 * Returns the trigonometric tangent of an [angle].
 */
fun tan(angle: Angle) = Math.tan(angle.rad.toDouble()).toFloat()

/**
 * Returns the angle _theta_ from the conversion of rectangular coordinates ([x],&nbsp;[y]) to polar coordinates (r,&nbsp;_theta_).
 */
fun atan2(y: Float, x: Float) = Math.atan2(y.toDouble(), x.toDouble()).radians
