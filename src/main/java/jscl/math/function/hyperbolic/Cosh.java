package jscl.math.function.hyperbolic;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegrableException;
import jscl.math.NotVariableException;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.Constants;
import jscl.math.function.Exp;
import jscl.math.function.Trigonometric;
import org.jetbrains.annotations.NotNull;

public class Cosh extends Trigonometric {
    public Cosh(Generic generic) {
        super("cosh",new Generic[] {generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return new Sinh(parameters[0]).evaluate();
    }

    public Generic derivative(int n) {
        return new Sinh(parameters[0]).evaluate();
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Cosh(parameters[0].negate()).evaluate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(1);
        }
        return expressionValue();
    }

    public Generic selfElementary() {
        return new Exp(
            parameters[0]
        ).selfElementary().add(
            new Exp(
                parameters[0].negate()
            ).selfElementary()
        ).multiply(Constants.Generic.HALF);
    }

    public Generic selfSimplify() {
        if(parameters[0].signum()<0) {
            return new Cosh(parameters[0].negate()).evaluate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(1);
        }
        try {
            Variable v= parameters[0].variableValue();
            if(v instanceof Acosh) {
                Generic g[]=((Acosh)v).getParameters();
                return g[0];
            }
        } catch (NotVariableException e) {}
        return identity();
    }

    public Generic identity(Generic a, Generic b) {
        return new Cosh(a).selfSimplify().multiply(
            new Cosh(b).selfSimplify()
        ).add(
            new Sinh(a).selfSimplify().multiply(
                new Sinh(b).selfSimplify()
            )
        );
    }

    public Generic selfNumeric() {
        return ((NumericWrapper) parameters[0]).cosh();
    }

    @NotNull
	public Variable newInstance() {
        return new Cosh(null);
    }
}
