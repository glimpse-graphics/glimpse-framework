package org.glimpseframework.api.internal.shader.parameters.parser;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ParameterParserResultTest {

	@Parameterized.Parameters(name = "{index}: {0} – {1} {2} {3}")
	public static Collection<Object[]> parameters() {
		List<Object[]> parameters = new LinkedList<Object[]>();
		for (Parameter.Scope scope : Parameter.Scope.values()) {
			for (Parameter.Type type : Parameter.Type.values()) {
				String name = String.format("%s_%s", scope.name().toLowerCase().charAt(0), type.getGLSLTypeName());
				String stringToParse = new Parameter(scope, type, name).toString();
				parameters.add(new Object[] { stringToParse, scope, type, name });
			}
		}
		return parameters;
	}

	public ParameterParserResultTest(String stringToParse, Parameter.Scope scope, Parameter.Type type, String name) {
		this.stringToParse = stringToParse;
		this.scope = scope;
		this.type = type;
		this.name = name;
	}

	@Test
	public void testScope() {
		// given:
		ParameterParser parser = new ParameterParser();
		// when:
		Parameter parameter = parser.parse(stringToParse).get(0);
		// then:
		assertEquals(scope, parameter.getScope());
	}

	@Test
	public void testType() {
		// given:
		ParameterParser parser = new ParameterParser();
		// when:
		Parameter parameter = parser.parse(stringToParse).get(0);
		// then:
		assertEquals(type, parameter.getType());
	}

	@Test
	public void testName() {
		// given:
		ParameterParser parser = new ParameterParser();
		// when:
		Parameter parameter = parser.parse(stringToParse).get(0);
		// then:
		assertEquals(name, parameter.getName());
	}

	private String stringToParse;
	private Parameter.Scope scope;
	private Parameter.Type type;
	private String name;
}
