package jscl.text;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 3:21 PM
 */
abstract class AbstractConverter<T, K> implements Parser<K> {
	@Nonnull
	protected final Parser<T> parser;

	AbstractConverter(@Nonnull Parser<T> parser) {
		this.parser = parser;
	}

}
