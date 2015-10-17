package org.glimpseframework.api.shader.parameters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParameterTest {

	@Test
	public void testToString() {
		// given:
		Parameter parameter = new Parameter(Parameter.Scope.UNIFORM, Parameter.Type.MATRIX_4, "u_mvpMatrix");
		// when:
		String string = parameter.toString();
		// then:
		assertEquals("uniform mat4 u_mvpMatrix;", string);
	}
}
