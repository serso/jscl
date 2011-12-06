package jscl;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.function.*;
import jscl.math.operator.Operator;
import jscl.math.operator.Percent;
import jscl.math.operator.matrix.OperatorsRegistry;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.MathRegistry;

/**
 * User: serso
 * Date: 11/1/11
 * Time: 12:00 PM
 */
public enum JsclMathEngine implements MathEngine {

	instance;

	@NotNull
	private AngleUnit angleUnits = AngleUnit.deg;

	@NotNull
	private NumeralBase numeralBase = NumeralBase.dec;

	@NotNull
	@Override
	public String evaluate(@NotNull String expression) throws ParseException {
	 	return evaluateGeneric(expression).toString();
	}

	@NotNull
	@Override
	public String simplify(@NotNull String expression) throws ParseException {
		return simplifyGeneric(expression).toString();
	}

	@NotNull
	@Override
	public String elementary(@NotNull String expression) throws ParseException {
		return elementaryGeneric(expression).toString();
	}

	@NotNull
	@Override
	public Generic evaluateGeneric(@NotNull String expression) throws ParseException {
		if (expression.contains(Percent.NAME)) {
			return Expression.valueOf(expression).numeric();
		} else {
			return Expression.valueOf(expression).expand().numeric();
		}
	}

	@NotNull
	@Override
	public Generic simplifyGeneric(@NotNull String expression) throws ParseException {
		if (expression.contains(Percent.NAME)) {
			return Expression.valueOf(expression);
		} else {
			return Expression.valueOf(expression).expand().simplify();
		}
	}

	@NotNull
	@Override
	public Generic elementaryGeneric(@NotNull String expression) throws ParseException {
		return Expression.valueOf(expression).elementary();
	}

	@NotNull
	@Override
	public MathRegistry<Function> getFunctionsRegistry() {
		return FunctionsRegistry.getInstance();
	}

	@NotNull
	@Override
	public MathRegistry<Operator> getOperatorsRegistry() {
		return OperatorsRegistry.getInstance();
	}

	@NotNull
	@Override
	public MathRegistry<Operator> getPostfixFunctionsRegistry() {
		return PostfixFunctionsRegistry.getInstance();
	}

	@NotNull
	@Override
	public AngleUnit getAngleUnits() {
		return angleUnits;
	}

	@Override
	public void setAngleUnits(@NotNull AngleUnit angleUnits) {
		this.angleUnits = angleUnits;
	}

	@Override
	@NotNull
	public NumeralBase getNumeralBase() {
		return numeralBase;
	}

	@Override
	public void setNumeralBase(@NotNull NumeralBase numeralBase) {
		this.numeralBase = numeralBase;
	}

	@NotNull
	@Override
	public MathRegistry<IConstant> getConstantsRegistry() {
		return ConstantsRegistry.getInstance();
	}
}
