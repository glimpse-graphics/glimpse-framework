package org.glimpseframework.api.annotations;

public class AnnotatedTestClass {

	@Attrib(type = DataType.FLOAT)
	private float aFloatField = 1.1f;

	@Attrib(name = "overriddenFloatAttrib", type = DataType.FLOAT)
	private float aFloatFieldOverridden = 2.2f;

	@Uniform(type = DataType.FLOAT)
	private float uFloatField = 3.3f;

	@Uniform(name = "overriddenFloatUniform", type = DataType.FLOAT)
	private float uFloatFieldOverridden = 4.4f;

	private float notAnnotatedField = 5.5f;

	@Attrib(type = DataType.INTEGER)
	public int getAttribInt() {
		return 11;
	}

	@Attrib(name = "overriddenIntAttrib", type = DataType.INTEGER)
	public int getAttribIntOverridden() {
		return 22;
	}

	@Uniform(type = DataType.INTEGER)
	public int getUniformInt() {
		return 33;
	}

	@Uniform(name = "overriddenIntUniform", type = DataType.INTEGER)
	public int getUniformIntOverridden() {
		return 44;
	}

	public int getNotAnnotatedMethod() {
		return 55;
	}
}
