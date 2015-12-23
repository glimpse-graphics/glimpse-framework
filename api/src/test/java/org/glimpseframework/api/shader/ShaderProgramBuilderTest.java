package org.glimpseframework.api.shader;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShaderProgramBuilderTest {

	private class ShaderProgramBuilderMock extends ShaderProgramBuilder<Shader, ShaderProgram> {

		@Override
		public ShaderProgramBuilder setSource(Shader.Type shaderType, String source) {
			addShader(shaders.get(shaderType));
			return this;
		}

		@Override
		protected ShaderProgram createProgram() {
			return shaderProgram;
		}
	}

	@Before
	public void setUpShaders() {
		when(vertexShader.getType()).thenReturn(Shader.Type.VERTEX_SHADER);
		when(fragmentShader.getType()).thenReturn(Shader.Type.FRAGMENT_SHADER);
		shaders.put(Shader.Type.VERTEX_SHADER, vertexShader);
		shaders.put(Shader.Type.FRAGMENT_SHADER, fragmentShader);
	}

	@Test
	public void testBuildWithoutError() throws Exception{
		// given:
		ShaderProgramBuilder<Shader, ShaderProgram> builder = new ShaderProgramBuilderMock();
		// when:
		ShaderProgram actual = builder
				.setSource(Shader.Type.VERTEX_SHADER, "VERTEX")
				.setSource(Shader.Type.FRAGMENT_SHADER, "FRAGMENT")
				.build();
		// then:
		assertSame(shaderProgram, actual);
		verify(vertexShader, times(1)).compile();
		verify(fragmentShader, times(1)).compile();
		verify(shaderProgram, times(1)).link();
	}

	@Test(expected = ShaderCompileException.class)
	public void testBuildCompileError() throws Exception{
		// given:
		doThrow(new ShaderCompileException("SHADER INFO LOG")).when(vertexShader).compile();
		ShaderProgramBuilder<Shader, ShaderProgram> builder = new ShaderProgramBuilderMock();
		// when:
		ShaderProgram actual = builder
				.setSource(Shader.Type.VERTEX_SHADER, "VERTEX")
				.setSource(Shader.Type.FRAGMENT_SHADER, "FRAGMENT")
				.build();
		// then: ShaderCompileException is thrown
	}

	@Test(expected = ShaderProgramLinkException.class)
	public void testBuildLinkError() throws Exception{
		// given:
		doThrow(new ShaderProgramLinkException("PROGRAM INFO LOG")).when(shaderProgram).link();
		ShaderProgramBuilder<Shader, ShaderProgram> builder = new ShaderProgramBuilderMock();
		// when:
		ShaderProgram actual = builder
				.setSource(Shader.Type.VERTEX_SHADER, "VERTEX")
				.setSource(Shader.Type.FRAGMENT_SHADER, "FRAGMENT")
				.build();
		// then: ShaderProgramLinkException is thrown
	}

	@Mock
	private Shader vertexShader;

	@Mock
	private Shader fragmentShader;

	private Map<Shader.Type, Shader> shaders = new EnumMap<Shader.Type, Shader>(Shader.Type.class);

	@Mock
	private ShaderProgram shaderProgram;
}
