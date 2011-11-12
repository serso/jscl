package jscl.math.function.hyperbolic;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegrableException;
import jscl.math.NotVariableException;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.Constant;
import jscl.math.function.Exp;
import jscl.math.function.Trigonometric;

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

    public Generic evaluateElementary() {
        return new Exp(
            parameters[0]
        ).evaluateElementary().add(
            new Exp(
                parameters[0].negate()
            ).evaluateElementary()
        ).multiply(Constant.half);
    }

    public Generic evaluateSimplify() {
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
        return new Cosh(a).evaluateSimplify().multiply(
            new Cosh(b).evaluateSimplify()
        ).add(
            new Sinh(a).evaluateSimplify().multiply(
                new Sinh(b).evaluateSimplify()
            )
        );
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).cosh();
    }

    public Variable newInstance() {
        return new Cosh(null);
    }
}
