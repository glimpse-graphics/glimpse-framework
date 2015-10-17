package org.glimpseframework.api.internal.shader.parameters.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parser of parameters in a shader.
 * @author Slawomir Czerwinski
 */
public class ParameterParser {

	/**
	 * Parses parameters in GLSL source code.
	 * @param source GLSL source code
	 * @return list of parameters
	 */
	public List<Parameter> parse(String source) {
		LOG.trace("Parsing parameters in shader source code:\n{}", source);
		List<Parameter> parameters = new ArrayList<Parameter>();
		Matcher matcher = PARAMETER_PATTERN.matcher(source);
		while (matcher.find()) {
			try {
				parameters.add(parseParameter(matcher.group(1), matcher.group(2), matcher.group(3)));
			} catch (IllegalArgumentException e) {
				LOG.trace("Invalid parameter definition.", e);
			}
		}
		return parameters;
	}

	private Parameter parseParameter(String scopeName, String typeName, String parameterName) {
		LOG.trace("Found shader parameter: {} {} {}", scopeName, typeName, parameterName);
		return new Parameter(
				Parameter.Scope.valueOf(scopeName.toUpperCase()),
				Parameter.Type.findByGLSLTypeName(typeName),
				parameterName);
	}

	private static final Logger LOG = LoggerFactory.getLogger(ParameterParser.class);

	private static final Pattern PARAMETER_PATTERN = Pattern.compile("\\s*(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*;\\s*");
}
