package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Main parser interface.
 *
 * Aim of parser is to convert input string expression into java objects
 *
 * @param <T> type of result object of parser
 */
public interface Parser<T> {

	/**
	 *
	 *
	 *
	 *
	 * @param expression expression to be parsed
	 * @param position current position of expression. Side effect: if parsing is successful this parameter should be increased on the number of parsed letters (incl whitespaces etc)
	 * @param previousSumElement
	 * @return parsed object of type T
	 *
	 * @throws ParseException occurs if object could not be parsed from the string
	 */
	T parse(@NotNull String expression,
			@NotNull MutableInt position,
			@Nullable Generic previousSumElement) throws ParseException;

}
