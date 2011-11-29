package jscl.text;

import jscl.AngleUnit;
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

	static class Parameters implements MathEngine{

		@NotNull
		private final String expression;

		@NotNull
		private final MutableInt position;

		@NotNull
		private final List<ParseException> exceptions = new ArrayList<ParseException>();

		@NotNull
		private final MathEngine mathEngine;

		/**
		 * @param expression expression to be parsed
		 * @param position current position of expression. Side effect: if parsing is successful this parameter should be increased on the number of parsed letters (incl whitespaces etc)
		 * @param mathEngine math engine to be used in parsing
		 */
		Parameters(@NotNull String expression, @NotNull MutableInt position, @NotNull MathEngine mathEngine) {
			this.expression = expression;
			this.position = position;
			this.mathEngine = mathEngine;
		}

		@NotNull
		public static Parameters newInstance(@NotNull String expression, @NotNull MutableInt position, @NotNull final MathEngine mathEngine) {
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
		public List<ParseException> getExceptions() {
			return Collections.unmodifiableList(exceptions);
		}


		@Override
		public String evaluate(@NotNull String expression) throws ParseException {
			return mathEngine.evaluate(expression);
		}

		@Override
		public String simplify(@NotNull String expression) throws ParseException {
			return mathEngine.simplify(expression);
		}

		@Override
		public String elementary(@NotNull String expression) throws ParseException {
			return mathEngine.elementary(expression);
		}

		@Override
		@NotNull
		public MathRegistry<Function> getFunctionsRegistry() {
			return mathEngine.getFunctionsRegistry();
		}

		@Override
		@NotNull
		public MathRegistry<Operator> getOperatorsRegistry() {
			return mathEngine.getOperatorsRegistry();
		}

		@Override
		@NotNull
		public MathRegistry<IConstant> getConstantsRegistry() {
			return mathEngine.getConstantsRegistry();
		}

		@Override
		@NotNull
		public MathRegistry<Operator> getPostfixFunctionsRegistry() {
			return mathEngine.getPostfixFunctionsRegistry();
		}

		@Override
		@NotNull
		public AngleUnit getDefaultAngleUnit() {
			return mathEngine.getDefaultAngleUnit();
		}

		@Override
		public void setDefaultAngleUnit(@NotNull AngleUnit defaultAngleUnits) {
			mathEngine.setDefaultAngleUnit(defaultAngleUnits);
		}
	}
}
