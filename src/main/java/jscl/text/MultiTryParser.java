package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 3:18 PM
 */
public class MultiTryParser<T> implements Parser<T> {

	@NotNull
	private final List<Parser<? extends T>> parsers;

	public MultiTryParser(@NotNull List<Parser<? extends T>> parsers) {
		this.parsers = parsers;
	}

	@Override
	public T parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		T result = null;

		for (final Iterator<Parser<? extends T>> it = parsers.iterator(); it.hasNext(); ) {
			try {
				result = it.next().parse(expression, position, depth, previousSumElement);
			} catch (ParseException e) {
				if (!it.hasNext()) {
					throw e;
				}
			}

			if (result != null) {
				break;
			}
		}

		return result;
	}
}
