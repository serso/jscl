package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
* User: serso
* Date: 10/27/11
* Time: 3:21 PM
*/
abstract class AbstractConverter<T, K> implements Parser<K>{
	@NotNull
	protected final Parser<T> parser;

	AbstractConverter(@NotNull Parser<T> parser) {
		this.parser = parser;
	}

}
