package jscl.math.function;

import jscl.math.*;
import jscl.mathml.MathML;

/**
 * User: serso
 * Date: 11/12/11
 * Time: 3:48 PM
 */
public class Rad extends Algebraic {

	public Rad(Generic generic) {
		super("rad", new Generic[]{generic});
	}

	@Override
	public Root rootValue() throws NotRootException {
		throw new UnsupportedOperationException("Root for rad() is not supported!");
	}

	@Override
	void bodyToMathML(MathML element, boolean fenced) {
		final MathML child = element.element("rad");
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
		return parameters[0].multiply(Constant.pi.numeric()).divide(JsclInteger.valueOf(180));
	}

	@Override
	public Generic derivative(int n) {
		throw new UnsupportedOperationException("Derivative for rad() is not supported!");
	}

	@Override
	public Variable newInstance() {
		return new Rad(null);
	}
}
