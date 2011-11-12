package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.Variable;
import jscl.mathml.MathML;

/**
 * User: serso
 * Date: 11/12/11
 * Time: 4:16 PM
 */
public class Deg extends Algebraic {

	public Deg(Generic generic) {
		super("deg", new Generic[]{generic});
	}

	@Override
	public Root rootValue() throws NotRootException {
		throw new UnsupportedOperationException("Root for deg() is not supported!");
	}

	@Override
	void bodyToMathML(MathML element, boolean fenced) {
		final MathML child = element.element("deg");
		parameters[0].toMathML(child, null);
		element.appendChild(child);
	}

	@Override
	public Generic evaluate() {
		return expressionValue();
	}

	@Override
	public Generic evaluateElementary() {
		return evaluate();
	}

	@Override
	public Generic evaluateSimplify() {
		return evaluate();
	}

	@Override
	public Generic evaluateNumerically() {
		return parameters[0].multiply(JsclInteger.valueOf(180)).divide(Constant.pi.numeric());
	}

	@Override
	public Generic derivative(int n) {
		throw new UnsupportedOperationException("Derivative for deg() is not supported!");
	}

	@Override
	public Variable newInstance() {
		return new Deg(null);
	}
}
