package jscl.math.function.trigonometric;

import jscl.math.*;
import jscl.math.function.Constant;
import jscl.math.function.Exp;
import jscl.math.function.Trigonometric;
import org.jetbrains.annotations.Nullable;

public class Cos extends Trigonometric {
    public Cos(Generic generic) {
        super("cos",new Generic[] {generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return new Sin(parameters[0]).evaluate();
    }

    public Generic derivative(int n) {
        return new Sin(parameters[0]).evaluate().negate();
    }

	public Generic evaluate() {
		final Generic result = trySimplify();

		if ( result != null ) {
			return result;
		} else {
			return expressionValue();
		}
	}

	@Nullable
	private Generic trySimplify() {
		Generic result = null;

		if (parameters[0].signum() < 0) {
			result = new Cos(parameters[0].negate()).evaluate();
		} else if (parameters[0].signum() == 0) {
			result = JsclInteger.valueOf(1);
		} else if (parameters[0].compareTo(Constant.pi) == 0) {
			result = JsclInteger.valueOf(-1);
		}

		return result;
	}

	public Generic evaluateElementary() {
        return new Exp(
            Constant.i.multiply(parameters[0])
        ).evaluateElementary().add(
            new Exp(
                Constant.i.multiply(parameters[0].negate())
            ).evaluateElementary()
        ).multiply(Constant.half);
    }

    public Generic evaluateSimplify() {
		final Generic result = trySimplify();

		if (result != null) {
			return result;
		} else {

			try {
				Variable v = parameters[0].variableValue();
				if (v instanceof Acos) {
					Generic g[] = ((Acos) v).getParameters();
					return g[0];
				}
			} catch (NotVariableException e) {
			}
			return identity();
		}
    }

    public Generic identity(Generic a, Generic b) {
        return new Cos(a).evaluateSimplify().multiply(
            new Cos(b).evaluateSimplify()
        ).subtract(
            new Sin(a).evaluateSimplify().multiply(
                new Sin(b).evaluateSimplify()
            )
        );
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).cos();
    }

    public Variable newInstance() {
        return new Cos(null);
    }
}
