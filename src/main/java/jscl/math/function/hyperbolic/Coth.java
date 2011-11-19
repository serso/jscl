package jscl.math.function.hyperbolic;

import jscl.math.*;
import jscl.math.function.Frac;
import jscl.math.function.Ln;
import jscl.math.function.Trigonometric;

public class Coth extends Trigonometric {
    public Coth(Generic generic) {
        super("coth",new Generic[] {generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return new Ln(
            JsclInteger.valueOf(4).multiply(
                new Sinh(parameters[0]).evaluate()
            )
        ).evaluate();
    }

    public Generic derivative(int n) {
        return JsclInteger.valueOf(1).subtract(
            new Coth(parameters[0]).evaluate().pow(2)
        );
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Coth(parameters[0].negate()).evaluate().negate();
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Frac(
            new Cosh(parameters[0]).evaluateElementary(),
            new Sinh(parameters[0]).evaluateElementary()
        ).evaluateElementary();
    }

    public Generic evaluateSimplify() {
        if(parameters[0].signum()<0) {
            return new Coth(parameters[0].negate()).evaluate().negate();
        }
        try {
            Variable v= parameters[0].variableValue();
            if(v instanceof Acoth) {
                Generic g[]=((Acoth)v).getParameters();
                return g[0];
            }
        } catch (NotVariableException e) {}
        return identity();
    }

    public Generic identity(Generic a, Generic b) {
        Generic ta=new Coth(a).evaluateSimplify();
        Generic tb=new Coth(b).evaluateSimplify();
        return new Frac(
            ta.multiply(tb).add(JsclInteger.valueOf(1)),
                        ta.add(tb)
        ).evaluateSimplify();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).coth();
    }

    public Variable newInstance() {
        return new Coth(null);
    }
}
