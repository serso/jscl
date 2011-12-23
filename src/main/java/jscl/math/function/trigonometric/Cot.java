package jscl.math.function.trigonometric;

import jscl.math.*;
import jscl.math.function.Frac;
import jscl.math.function.Ln;
import jscl.math.function.Trigonometric;

public class Cot extends Trigonometric {
    public Cot(Generic generic) {
        super("cot",new Generic[] {generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return new Ln(
            JsclInteger.valueOf(4).multiply(
                new Sin(parameters[0]).evaluate()
            )
        ).evaluate();
    }

    public Generic derivative(int n) {
        return JsclInteger.valueOf(1).add(
            new Cot(parameters[0]).evaluate().pow(2)
        ).negate();
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Cot(parameters[0].negate()).evaluate().negate();
        }
        return expressionValue();
    }

    public Generic selfElementary() {
        return new Frac(
            new Cos(parameters[0]).selfElementary(),
            new Sin(parameters[0]).selfElementary()
        ).selfElementary();
    }

    public Generic selfSimplify() {
        if(parameters[0].signum()<0) {
            return new Cot(parameters[0].negate()).evaluate().negate();
        }
        try {
            Variable v= parameters[0].variableValue();
            if(v instanceof Acot) {
                Generic g[]=((Acot)v).getParameters();
                return g[0];
            }
        } catch (NotVariableException e) {}
        return identity();
    }

    public Generic identity(Generic a, Generic b) {
        Generic ta=new Cot(a).selfSimplify();
        Generic tb=new Cot(b).selfSimplify();
        return new Frac(
            ta.multiply(tb).subtract(JsclInteger.valueOf(1)),
                        ta.add(tb)
        ).selfSimplify();
    }

    public Generic selfNumeric() {
        return ((NumericWrapper) parameters[0]).cot();
    }

    public Variable newInstance() {
        return new Cot(null);
    }
}
