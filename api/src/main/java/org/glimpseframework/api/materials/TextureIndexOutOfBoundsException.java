package org.glimpseframework.api.materials;

import org.glimpseframework.api.GlimpseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextureIndexOutOfBoundsException extends GlimpseException {

	public TextureIndexOutOfBoundsException(int actual, int max) {
		super(String.format(MESSAGE_FORMAT, actual, max));
		LOG.error(getMessage(), this);
	}

	private static final String MESSAGE_FORMAT = "Texture index out of bounds: <%d>. Expected between 0\u2014%d.";

	private static final Logger LOG = LoggerFactory.getLogger(Texture.class);
}
