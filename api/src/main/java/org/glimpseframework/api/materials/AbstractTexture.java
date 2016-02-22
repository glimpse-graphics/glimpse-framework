package org.glimpseframework.api.materials;

public abstract class AbstractTexture implements Texture {

	public AbstractTexture() {
		index = 0;
	}

	public AbstractTexture(int index) {
		this.index = index;
	}

	@Override
	public final int getIndex() {
		return index;
	}

	@Override
	public final int getId() {
		return id[0];
	}

	private final int index;

	protected final int[] id = new int[1];
}
