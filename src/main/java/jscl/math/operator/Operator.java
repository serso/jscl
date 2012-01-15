package jscl.math.operator;

import jscl.math.*;
import org.jetbrains.annotations.NotNull;

public abstract class Operator extends AbstractFunction {

	protected Operator(String name, Generic parameters[]) {
		super(name, parameters);
	}

	public Generic antiDerivative(Variable variable) throws NotIntegrableException {
		throw new NotIntegrableException(this);
	}

	@NotNull
	public Generic derivative(Variable variable) {
		if (isIdentity(variable)) {
			return JsclInteger.valueOf(1);
		} else {
			return JsclInteger.valueOf(0);
		}
	}

	@Override
	public Generic selfElementary() {
		return expressionValue();
	}

	@Override
	public Generic selfSimplify() {
		return expressionValue();
	}

	@Override
	public Generic selfNumeric() {
		return numeric();
	}

	public Generic numeric() {
		throw new ArithmeticException();
	}

	public boolean isConstant(Variable variable) {
		return !isIdentity(variable);
	}

	@NotNull
	protected static Variable[] toVariables(@NotNull Generic vector) throws NotVariableException {
		return toVariables((JsclVector)vector);
	}

	@NotNull
	protected static Variable[] toVariables(@NotNull JsclVector vector) throws NotVariableException {
		final Generic element[] = vector.elements();
		final Variable variable[] = new Variable[element.length];

		for (int i = 0; i < element.length; i++) {
			variable[i] = element[i].variableValue();
		}

		return variable;
	}

	@NotNull
	public abstract Operator newInstance(@NotNull Generic[] parameters);
}
