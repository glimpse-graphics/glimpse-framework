package org.glimpseframework.api;

import java.util.LinkedList;
import java.util.List;
import org.glimpseframework.api.models.Model;
import org.glimpseframework.api.shader.ShaderProgram;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;
import org.glimpseframework.internal.shader.parameters.ParametersManager;
import org.glimpseframework.internal.shader.parameters.resolver.ResolveParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OpenGL scene.
 * @author Slawomir Czerwinski
 */
public abstract class Scene {

	/**
	 * Creates a scene.
	 */
	protected Scene() {
		parametersManager = new ParametersManager();
	}

	/**
	 * Gets shader parameter adapter for the specific shader program.
	 * @param shaderProgram shader program
	 * @return shader parameter adapter implementation
	 */
	protected abstract ShaderParameterAdapter getAdapter(ShaderProgram shaderProgram);

	/**
	 * Registers an object providing values for shader parameters.
	 * @param object object
	 */
	public final void register(Object object) {
		parametersManager.registerValueObject(object);
	}

	/**
	 * Unregisters an object providing values for shader parameters.
	 * @param object object
	 */
	public final void unregister(Object object) {
		parametersManager.unregisterValueObject(object);
	}

	/**
	 * Invoked once, before the scene is first rendered.
	 */
	protected void onCreate() {
	}

	/**
	 * Invoked when the viewport is resized.
	 * @param width new viewport width
	 * @param height new viewport height
	 */
	protected void onResize(int width, int height) {
	}

	protected final void render() {
		onPreRender();
		doRender();
		onPostRender();
	}

	/**
	 * Invoked each time before the scene is rendered.
	 */
	protected void onPreRender() {
	}

	private void doRender() {
		for (Model model : models) {
			try {
				renderModel(model);
			} catch (ResolveParameterException e) {
				LOG.error("Could not render model", e);
			}
		}
	}

	private void renderModel(Model model) throws ResolveParameterException {
		parametersManager.registerValueObject(model);
		parametersManager.applyParameters(model.getShaderProgram());
		getAdapter(model.getShaderProgram()).drawTriangles(model.getNumberOfVertices());
		parametersManager.unregisterValueObject(model);
	}

	/**
	 * Invoked each time after the scene is rendered.
	 */
	protected void onPostRender() {
	}

	private static final Logger LOG = LoggerFactory.getLogger(Scene.class);

	private ParametersManager parametersManager;

	private List<Model> models = new LinkedList<Model>();
}
