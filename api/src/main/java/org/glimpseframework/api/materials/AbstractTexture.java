package org.glimpseframework.api.materials;

/**
 * Abstract implementation of OpenGL texture.
 * @author Slawomir Czerwinski
 */
public abstract class AbstractTexture implements Texture {

	/**
	 * Creates a new texture.
	 */
	public AbstractTexture() {
		index = 0;
	}

	/**
	 * Creates a new texture with a specific index.
	 * @param index texture index
	 * @throws TextureIndexOutOfBoundsException when index is out of supported range
	 */
	public AbstractTexture(int index) throws TextureIndexOutOfBoundsException {
		validateTextureIndex(index);
		this.index = index;
	}

	private void validateTextureIndex(int index) throws TextureIndexOutOfBoundsException {
		int max = getMaxTextureImageUnits();
		if (index < 0 || index >= max) {
			throw new TextureIndexOutOfBoundsException(index, max);
		}
	}

	/**
	 * Gets maximum texture image units for fragment shader.
	 * @return maximum texture image units for fragment shader
	 */
	protected abstract int getMaxTextureImageUnits();

	@Override
	public final int getIndex() {
		return index;
	}

	@Override
	public final int getId() {
		return id[0];
	}

	private final int index;

	/**
	 * Texture ID.
	 */
	protected final int[] id = new int[1];
}
