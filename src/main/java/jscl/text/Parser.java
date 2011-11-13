package jscl.text;

import org.jetbrains.annotations.NotNull;

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
	 * @param expression expression to be parsed
	 * @param position current position of expression. Side effect: if parsing is successful this parameter should be increased on the number of parsed letters (incl whitespaces etc)
	 * @param depth current depth of parsing (should be passed recursively from one parser to another, initial value is set in initial parser, incrementation also provided by initial parser)
	 *
	 * @return parsed object of type T
	 *
	 * @throws ParseException occurs if object could not be parsed from the string
	 */
	T parse(@NotNull String expression,
			@NotNull MutableInt position,
			int depth) throws ParseException;

}
