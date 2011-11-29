package jscl.text;

import jscl.AngleUnit;
import jscl.MathContext;
import jscl.MathEngine;
import jscl.math.Generic;
import jscl.math.function.Function;
import jscl.math.function.IConstant;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.MathRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	 * @param p parse parameters
	 * @param previousSumElement sum element to the left of last + sign
	 * @return parsed object of type T
	 *
	 * @throws ParseException occurs if object could not be parsed from the string
	 */
	T parse(@NotNull Parameters p, @Nullable Generic previousSumElement) throws ParseException;

	static class Parameters {

		@NotNull
		private final String expression;

		@NotNull
		private final MutableInt position;

		@NotNull
		private final List<ParseException> exceptions = new ArrayList<ParseException>();

		@NotNull
		private final MathContext mathContext;

		/**
		 * @param expression expression to be parsed
		 * @param position current position of expression. Side effect: if parsing is successful this parameter should be increased on the number of parsed letters (incl whitespaces etc)
		 * @param mathContext math engine to be used in parsing
		 */
		Parameters(@NotNull String expression, @NotNull MutableInt position, @NotNull MathContext mathContext) {
			this.expression = expression;
			this.position = position;
			this.mathContext = mathContext;
		}

		@NotNull
		public static Parameters newInstance(@NotNull String expression, @NotNull MutableInt position, @NotNull final MathContext mathEngine) {
			return new Parameters(expression, position, mathEngine);
		}

		@NotNull
		public String getExpression() {
			return expression;
		}

		@NotNull
		public MutableInt getPosition() {
			return position;
		}

		public void addException(@NotNull ParseException e) {
			if (!exceptions.contains(e)) {
				exceptions.add(e);
			}
		}

		@NotNull
		public MathContext getMathContext() {
			return mathContext;
		}

		@NotNull
		public List<ParseException> getExceptions() {
			return Collections.unmodifiableList(exceptions);
		}
	}
}
