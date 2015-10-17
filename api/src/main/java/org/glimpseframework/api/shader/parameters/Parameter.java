package org.glimpseframework.api.shader.parameters;

import java.lang.annotation.Annotation;
import java.util.Objects;
import org.glimpseframework.api.annotations.Attribute;
import org.glimpseframework.api.annotations.Uniform;

/**
 * OpenGL shader parameter.
 * @author Slawomir Czerwinski
 */
public final class Parameter {

	/**
	 * Parameter scope in shader.
	 */
	public enum Scope {

		/**
		 * Attribute parameters are defined per vertex. They can only be read by a vertex shader.
		 */
		ATTRIBUTE(Attribute.class),

		/**
		 * Uniform parameters are constant within a single model being rendered by a shader.
		 */
		UNIFORM(Uniform.class);

		Scope(Class<? extends Annotation> annotation) {
			this.annotation = annotation;
		}

		private Class<? extends Annotation> annotation;
	}

	/**
	 * Parameter type.
	 */
	public enum Type {
		INTEGER("int"),
		FLOAT("float"),
		UNSIGNED_INTEGER("uint"),
		BOOLEAN("bool"),
		FLOAT_VECTOR_2("vec2"),
		FLOAT_VECTOR_3("vec3"),
		FLOAT_VECTOR_4("vec4"),
		INTEGER_VECTOR_2("ivec2"),
		INTEGER_VECTOR_3("ivec3"),
		INTEGER_VECTOR_4("ivec4"),
		BOOLEAN_VECTOR_2("bvec2"),
		BOOLEAN_VECTOR_3("bvec3"),
		BOOLEAN_VECTOR_4("bvec4"),
		MATRIX_2("mat2"),
		MATRIX_3("mat3"),
		MATRIX_4("mat4"),
		SAMPLER_1D("sampler1D"),
		SAMPLER_2D("sampler2D"),
		SAMPLER_3D("sampler3D"),
		SAMPLER_CUBE("samplerCube"),
		SAMPLER_1D_SHADOW("sampler1DShadow"),
		SAMPLER_2D_SHADOW("sampler2DShadow");

		Type(String glslTypeName) {
			this.glslTypeName = glslTypeName;
		}

		/**
		 * Finds parameter type by GLSL type name.
		 * @param glslTypeName GLSL type name
		 * @return parameter type
		 */
		public static Type findByGLSLTypeName(String glslTypeName) {
			for (Type type : values()) {
				if (type.glslTypeName.equals(glslTypeName)) {
					return type;
				}
			}
			throw new IllegalArgumentException(String.format("GLSL type not supported: %s", glslTypeName));
		}

		/**
		 * Gets GLSL type name of the parameter type.
		 * @return GLSL type name
		 */
		public String getGLSLTypeName() {
			return glslTypeName;
		}

		private String glslTypeName;
	}

	/**
	 * Creates a new parameter.
	 * @param scope parameter scope
	 * @param type parameter type
	 * @param name parameter name
	 */
	public Parameter(Scope scope, Type type, String name) {
		this.scope = scope;
		this.type = type;
		this.name = name;
	}

	/**
	 * Gets parameter scope.
	 * @return parameter scope
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * Gets parameter type.
	 * @return parameter type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Gets parameter name.
	 * @return parameter name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s;", scope.name().toLowerCase(), type.glslTypeName, name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Parameter parameter = (Parameter) o;

		if (scope != parameter.scope) {
			return false;
		}
		if (type != parameter.type) {
			return false;
		}
		return !(name != null ? !name.equals(parameter.name) : parameter.name != null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(scope, type, name);
	}

	private final Scope scope;
	private final Type type;
	private final String name;
}
