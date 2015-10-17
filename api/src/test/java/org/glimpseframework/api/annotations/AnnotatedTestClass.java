package org.glimpseframework.api.annotations;

public class AnnotatedTestClass {

	@Attribute
	private float aFloatField = 1.1f;

	@Attribute(name = "overriddenFloatAttrib")
	private float aFloatFieldOverridden = 2.2f;

	@Uniform
	private float uFloatField = 3.3f;

	@Uniform(name = "overriddenFloatUniform")
	private float uFloatFieldOverridden = 4.4f;

	private float notAnnotatedField = 5.5f;

	@Attribute
	public int getAttribInt() {
		return 11;
	}

	@Attribute(name = "overriddenIntAttrib")
	public int getAttribIntOverridden() {
		return 22;
	}

	@Uniform
	public int getUniformInt() {
		return 33;
	}

	@Uniform(name = "overriddenIntUniform")
	public int getUniformIntOverridden() {
		return 44;
	}

	public int getNotAnnotatedMethod() {
		return 55;
	}
}
