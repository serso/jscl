package jscl;

import jscl.math.Expression;
import jscl.math.function.*;
import jscl.math.operator.Operator;
import jscl.math.operator.matrix.OperatorsRegistry;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.MathRegistry;

/**
 * User: serso
 * Date: 11/1/11
 * Time: 12:00 PM
 */
public class JsclMathEngine implements MathEngine {

	@Override
	public String evaluate(@NotNull String expression) throws ParseException {
		return Expression.valueOf(expression).expand().numeric().toString();
	}

	@Override
	public String simplify(@NotNull String expression) throws ParseException {
		return Expression.valueOf(expression).simplify().toString();
	}

	@Override
	public String elementary(@NotNull String expression) throws ParseException {
		return Expression.valueOf(expression).elementary().toString();
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
	public MathRegistry<IConstant> getConstantsRegistry() {
		return ConstantsRegistry.getInstance();
	}
}
