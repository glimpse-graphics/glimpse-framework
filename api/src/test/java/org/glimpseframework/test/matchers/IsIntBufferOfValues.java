package org.glimpseframework.test.matchers;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import org.mockito.ArgumentMatcher;

public class IsIntBufferOfValues extends ArgumentMatcher<Buffer> {

	private int values[];

	public IsIntBufferOfValues(int[] values) {
		this.values = values;
	}

	@Override
	public boolean matches(Object argument) {
		if (argument instanceof IntBuffer) {
			IntBuffer buffer = (IntBuffer) argument;
			return Arrays.equals(values, buffer.array());
		}
		return false;
	}
}
