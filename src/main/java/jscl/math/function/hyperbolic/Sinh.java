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

public class Sinh extends Trigonometric {
    public Sinh(Generic generic) {
        super("sinh",new Generic[] {generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return new Cosh(parameters[0]).evaluate();
    }

    public Generic derivative(int n) {
        return new Cosh(parameters[0]).evaluate();
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Sinh(parameters[0].negate()).evaluate().negate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic selfElementary() {
        return new Exp(
            parameters[0]
        ).selfElementary().subtract(
            new Exp(
                parameters[0].negate()
            ).selfElementary()
        ).multiply(Constant.half);
    }

    public Generic selfSimplify() {
        if(parameters[0].signum()<0) {
            return new Sinh(parameters[0].negate()).evaluate().negate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(0);
        }
        try {
            Variable v= parameters[0].variableValue();
            if(v instanceof Asinh) {
                Generic g[]=((Asinh)v).getParameters();
                return g[0];
            }
        } catch (NotVariableException e) {}
        return identity();
    }

    public Generic identity(Generic a, Generic b) {
        return new Cosh(b).selfSimplify().multiply(
            new Sinh(a).selfSimplify()
        ).add(
            new Cosh(a).selfSimplify().multiply(
                new Sinh(b).selfSimplify()
            )
        );
    }

    public Generic selfNumeric() {
        return ((NumericWrapper) parameters[0]).sinh();
    }

    public Variable newInstance() {
        return new Sinh(null);
    }
}
