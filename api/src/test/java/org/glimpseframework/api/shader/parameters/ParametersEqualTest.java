package org.glimpseframework.api.shader.parameters;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ParametersEqualTest {

	@Parameterized.Parameters(name = "{index}: {0} vs. {1} – {2}")
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{
						CONST_PARAMETER,
						CONST_PARAMETER,
						true
				},
				{
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						true
				},
				{
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						new Parameter(Parameter.Scope.ATTRIBUTE, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						false
				},
				{
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_3, "u_mvpMatrix"),
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						false
				},
				{
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_modelMatrix"),
						false
				},
				{
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						null,
						false
				},
				{
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						"uniform mat4 u_mvpMatrix;",
						false
				},
				{
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, null),
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix"),
						false
				},
				{
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, null),
						new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, null),
						true
				},
		});
	}

	public ParametersEqualTest(Parameter p1, Object p2, boolean expectedResult) {
		this.p1 = p1;
		this.p2 = p2;
		this.expectedResult = expectedResult;
	}

	@Test
	public void testEqual() {
		// given: p1, p2
		// when:
		boolean result = p1.equals(p2);
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testHashCodesEqual() {
		// given: p1, p2
		// when:
		boolean result = (p2 != null) && (p1.hashCode() == p2.hashCode());
		// then:
		Assert.assertEquals(expectedResult, result);
	}

	private static final Parameter CONST_PARAMETER =
			new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix");

	private Parameter p1;
	private Object p2;
	private boolean expectedResult;
}