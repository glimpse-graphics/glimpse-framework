package org.glimpseframework.test.matchers;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Arrays;
import org.mockito.ArgumentMatcher;

public class IsFloatBufferOfValues extends ArgumentMatcher<Buffer> {

	private float values[];

	public IsFloatBufferOfValues(float[] values) {
		this.values = values;
	}

	@Override
	public boolean matches(Object argument) {
		if (argument instanceof FloatBuffer) {
			FloatBuffer buffer = (FloatBuffer) argument;
			buffer.rewind();
			if (buffer.capacity() != values.length) {
				return false;
			}
			for (float value : values) {
				if (buffer.get() != value) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
