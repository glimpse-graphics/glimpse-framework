package org.glimpseframework.api.internal.shader.parameters.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.junit.Test;

public class ParameterParserTest {

	@Test
	public void testParse() throws IOException {
		// given:
		String source = loadSource();
		// when:
		Set<Parameter> parameters = new ParameterParser().parse(source);
		// then:
		assertEquals(6, parameters.size());
	}

	private String loadSource() throws IOException {
		StringBuilder builder = new StringBuilder();
		InputStream inputStream = getClass().getResourceAsStream(SOURCE_FILE);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line).append('\n');
		}
		return builder.toString();
	}

	@Test
	public void testInvalidScope() {
		// given:
		String source = "foo int boo;";
		// when:
		Set<Parameter> parameters = new ParameterParser().parse(source);
		// then:
		assertTrue(parameters.isEmpty());
	}

	@Test
	public void testInvalidType() {
		// given:
		String source = "uniform foo boo;";
		// when:
		Set<Parameter> parameters = new ParameterParser().parse(source);
		// then:
		assertTrue(parameters.isEmpty());
	}

	private static final String SOURCE_FILE = "parametersParserTest.glsl";
}
