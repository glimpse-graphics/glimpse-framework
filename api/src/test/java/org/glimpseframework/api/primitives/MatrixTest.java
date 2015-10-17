package org.glimpseframework.api.primitives;

import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatrixTest {

	@Before
	public void setUpLocale() {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	public void test16f() {
		// given:
		Matrix m = new Matrix(
				1.0f, 2.0f, 3.0f, 4.0f,
				5.0f, 6.0f, 7.0f, 8.0f,
				9.0f, 10.0f, 11.0f, 12.0f,
				13.0f, 14.0f, 15.0f, 16.0f);
		// when:
		float[] matrix16f = m.get16f();
		// then:
		float[] expected = {
				1.0f, 2.0f, 3.0f, 4.0f,
				5.0f, 6.0f, 7.0f, 8.0f,
				9.0f, 10.0f, 11.0f, 12.0f,
				13.0f, 14.0f, 15.0f, 16.0f};
		Assert.assertArrayEquals(expected, matrix16f, DELTA);
	}

	@Test(expected = InvalidMatrixSizeException.class)
	public void testCreateMatrixInvalidArguments() {
		// when:
		new Matrix(
				1.0f, 2.0f, 3.0f, 4.0f,
				5.0f, 6.0f, 7.0f, 8.0f,
				9.0f, 10.0f, 11.0f, 12.0f,
				13.0f, 14.0f, 15.0f);
	}

	@Test
	public void testPerspective() {
		// when:
		Matrix perspectiveMatrix = Matrix.getPerspectiveMatrix(Angle.fromDegrees(90.0f), 1.5f, 1.0f, 10.0f);
		// then:
		Matrix expected = Matrix.getFrustumMatrix(-1.5f, 1.5f, -1.0f, 1.0f, 1.0f, 10.0f);
		assertEquals(expected, perspectiveMatrix);
	}

	@Test
	public void testFrustum() {
		// when:
		Matrix frustumMatrix = Matrix.getFrustumMatrix(-100.0f, 100.0f, -100.0f, 100.0f, 1.0f, 10.0f);
		// then:
		Matrix expected = new Matrix(
				0.01f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.01f, 0.0f, 0.0f,
				0.0f, 0.0f, -1.2222f, -1.0f,
				0.0f, 0.0f, -2.2222f, 0.0f);
		assertEquals(expected, frustumMatrix);
	}

	@Test
	public void testOrthographic() {
		// when:
		Matrix frustumMatrix = Matrix.getOrthographicMatrix(-100.0f, 100.0f, -100.0f, 100.0f, 1.0f, 10.0f);
		// then:
		Matrix expected = new Matrix(
				0.01f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.01f, 0.0f, 0.0f,
				0.0f, 0.0f, -0.2222f, 0.0f,
				0.0f, 0.0f, 1.2222f, 1.0f);
		assertEquals(expected, frustumMatrix);
	}

	@Test
	public void testLookAt() {
		// given:
		Point eye = new Point(0.0f, -10.0f, 0.0f);
		Point center = new Point(0.0f, 0.0f, 0.0f);
		Vector up = Vector.Z_UNIT_VECTOR;
		// when:
		Matrix lookAtMatrix = Matrix.getLookAtMatrix(eye, center, up);
		// then:
		Matrix expected = new Matrix(
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, -1.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, -10.0f, 1.0f);
		assertEquals(expected, lookAtMatrix);
	}

	@Test
	public void testXRotationMatrix() {
		// given:
		Vector axis = Vector.X_UNIT_VECTOR;
		Angle angle = Angle.fromDegrees(21.5f);
		// when:
		Matrix rotationMatrix = Matrix.getRotationMatrix(axis, angle);
		// then:
		assertEquals(Matrix.getXRotationMatrix(angle), rotationMatrix);
	}

	@Test
	public void testYRotationMatrix() {
		// given:
		Vector axis = Vector.Y_UNIT_VECTOR;
		Angle angle = Angle.fromDegrees(117.3f);
		// when:
		Matrix rotationMatrix = Matrix.getRotationMatrix(axis, angle);
		// then:
		assertEquals(Matrix.getYRotationMatrix(angle), rotationMatrix);
	}

	@Test
	public void testZRotationMatrix() {
		// given:
		Vector axis = Vector.Z_UNIT_VECTOR;
		Angle angle = Angle.fromDegrees(213.7f);
		// when:
		Matrix rotationMatrix = Matrix.getRotationMatrix(axis, angle);
		// then:
		assertEquals(Matrix.getZRotationMatrix(angle), rotationMatrix);
	}

	@Test
	public void testRotationAngleReversible() {
		// given:
		Vector axis = new Vector(10.0f, 20.0f, 30.0f);
		Angle angle = Angle.fromDegrees(17.5f);
		Angle reverseAngle = Angle.fromDegrees(-17.5f);
		// when:
		Matrix rotationMatrix = Matrix.getRotationMatrix(axis, angle);
		Matrix reverseRotationMatrix = Matrix.getRotationMatrix(axis, reverseAngle);
		Matrix result = rotationMatrix.multiply(reverseRotationMatrix);
		// then:
		assertEquals(Matrix.IDENTITY_MATRIX, result);
	}

	@Test
	public void testRotationAxisReversible() {
		// given:
		Vector axis = new Vector(10.0f, 20.0f, 30.0f);
		Vector reverseAxis = new Vector(-10.0f, -20.0f, -30.0f);
		Angle angle = Angle.fromDegrees(17.5f);
		// when:
		Matrix rotationMatrix = Matrix.getRotationMatrix(axis, angle);
		Matrix reverseRotationMatrix = Matrix.getRotationMatrix(reverseAxis, angle);
		Matrix result = rotationMatrix.multiply(reverseRotationMatrix);
		// then:
		assertEquals(Matrix.IDENTITY_MATRIX, result);
	}

	@Test
	public void testRotateCoordinateSystem() {
		// given:
		Vector axis = new Vector(1.0f, 1.0f, 1.0f);
		Angle angle = Angle.fromDegrees(120.0f);
		// when:
		Matrix rotationMatrix = Matrix.getRotationMatrix(axis, angle);
		// then:
		Matrix expected = new Matrix(
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
		assertEquals(expected, rotationMatrix);
	}

	@Test
	public void testMultiply() {
		// given:
		Matrix m1 = new Matrix(
				1.0f, 2.0f, 3.0f, 4.0f,
				5.0f, 6.0f, 7.0f, 8.0f,
				9.0f, 10.0f, 11.0f, 12.0f,
				13.0f, 14.0f, 15.0f, 16.0f);
		Matrix m2 = new Matrix(
				21.0f, 22.0f, 23.0f, 24.0f,
				25.0f, 26.0f, 27.0f, 28.0f,
				29.0f, 30.0f, 31.0f, 32.0f,
				33.0f, 34.0f, 35.0f, 36.0f);
		// when:
		Matrix product = m1.multiply(m2);
		// then:
		Matrix expected = new Matrix(
				650.0f, 740.0f, 830.0f, 920.0f,
				762.0f, 868.0f, 974.0f, 1080.0f,
				874.0f, 996.0f, 1118.0f, 1240.0f,
				986.0f, 1124.0f, 1262.0f, 1400.0f);
		assertEquals(expected, product);
	}

	@Test
	public void testMultiplyIdentity() {
		// given:
		Matrix m = new Matrix(
				1.0f, 2.0f, 3.0f, 4.0f,
				5.0f, 6.0f, 7.0f, 8.0f,
				9.0f, 10.0f, 11.0f, 12.0f,
				13.0f, 14.0f, 15.0f, 16.0f);
		// when:
		Matrix product = Matrix.IDENTITY_MATRIX.multiply(m);
		// then:
		assertEquals(m, product);
	}

	@Test
	public void testMultiplyByIdentity() {
		// given:
		Matrix m = new Matrix(
				1.0f, 2.0f, 3.0f, 4.0f,
				5.0f, 6.0f, 7.0f, 8.0f,
				9.0f, 10.0f, 11.0f, 12.0f,
				13.0f, 14.0f, 15.0f, 16.0f);
		// when:
		Matrix product = m.multiply(Matrix.IDENTITY_MATRIX);
		// then:
		assertEquals(m, product);
	}

	@Test
	public void testTranspose() {
		// given:
		Matrix m = new Matrix(
				1.0f, 2.0f, 3.0f, 4.0f,
				5.0f, 6.0f, 7.0f, 8.0f,
				9.0f, 10.0f, 11.0f, 12.0f,
				13.0f, 14.0f, 15.0f, 16.0f);
		// when:
		Matrix transposed = m.transpose();
		// then:
		Matrix expected = new Matrix(
				1.0f, 5.0f, 9.0f, 13.0f,
				2.0f, 6.0f, 10.0f, 14.0f,
				3.0f, 7.0f, 11.0f, 15.0f,
				4.0f, 8.0f, 12.0f, 16.0f);
		assertEquals(expected, transposed);
	}

	@Test
	public void testInvert() {
		// given:
		Matrix m = new Matrix(
				4.0f, 9.0f, 5.0f, 6.0f,
				2.0f, 7.0f, 8.0f, 10.0f,
				11.0f, 12.0f, 13.0f, 14.0f,
				15.0f, 16.0f, 17.0f, 18.0f);
		// when:
		Matrix inverted = m.invert();
		// then:
		assertEquals(Matrix.IDENTITY_MATRIX, m.multiply(inverted));
	}

	@Test
	public void testNormalMatrix() {
		// given:
		Matrix m = new Matrix(
				4.0f, 9.0f, 5.0f, 6.0f,
				2.0f, 7.0f, 8.0f, 10.0f,
				11.0f, 12.0f, 13.0f, 14.0f,
				15.0f, 16.0f, 17.0f, 18.0f);
		// when:
		Matrix normal = m.getNormalMatrix();
		// then:
		assertEquals(Matrix.IDENTITY_MATRIX, m.transpose().multiply(normal));
	}

	@Test
	public void testTranslate() {
		// given:
		Matrix m = Matrix.IDENTITY_MATRIX;
		Vector v = new Vector(2.0f, 4.0f, 6.0f);
		// when:
		Matrix translated = m.translate(v);
		// then:
		Matrix expected = new Matrix(
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				2.0f, 4.0f, 6.0f, 1.0f);
		assertEquals(expected, translated);
	}

	@Test
	public void testRotate() {
		// given:
		Matrix m = Matrix.IDENTITY_MATRIX;
		Vector axis = new Vector(1.0f, 1.0f, 1.0f);
		Angle angle = Angle.fromDegrees(120.0f);
		// when:
		Matrix rotated = m.rotate(axis, angle);
		// then:
		Matrix expected = new Matrix(
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
		assertEquals(expected, rotated);
	}

	@Test
	public void testScale() {
		// given:
		Matrix m = Matrix.IDENTITY_MATRIX;
		float scale = 2.0f;
		// when:
		Matrix scaled = m.scale(scale);
		// then:
		Matrix expected = new Matrix(
				2.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 2.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 2.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
		assertEquals(expected, scaled);
	}

	@Test
	public void testToString() {
		// given:
		Matrix m = new Matrix(
				1.0f, 2.0f, 3.0f, 4.0f,
				5.0f, 6.0f, 7.0f, 8.0f,
				9.0f, 10.0f, 11.0f, 12.0f,
				13.0f, 14.0f, 15.0f, 16.0f);
		// when:
		String string = m.toString();
		// then:
		String expected =
				"\n|     1.00     5.00     9.00    13.00 |" +
				"\n|     2.00     6.00    10.00    14.00 |" +
				"\n|     3.00     7.00    11.00    15.00 |" +
				"\n|     4.00     8.00    12.00    16.00 |\n";
		Assert.assertEquals(expected, string);
	}

	private void assertEquals(Matrix expected, Matrix actual) {
		Assert.assertArrayEquals(expected.get16f(), actual.get16f(), DELTA);
	}

	public static final float DELTA = 0.0001f;
}
