package jscl.math.function.hyperbolic;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.math.function.Frac;
import jscl.math.function.Lg;
import jscl.math.function.Trigonometric;

public class Tanh extends Trigonometric {
    public Tanh(Generic generic) {
        super("tanh",new Generic[] {generic});
    }

    public Generic antiderivative(int n) throws NotIntegrableException {
        return new Lg(
            JsclInteger.valueOf(4).multiply(
                new Cosh(parameter[0]).evaluate()
            )
        ).evaluate();
    }

    public Generic derivative(int n) {
        return JsclInteger.valueOf(1).subtract(
            new Tanh(parameter[0]).evaluate().pow(2)
        );
    }

    public Generic evaluate() {
        if(parameter[0].signum()<0) {
            return new Tanh(parameter[0].negate()).evaluate().negate();
        } else if(parameter[0].signum()==0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Frac(
            new Sinh(parameter[0]).evaluateElementary(),
            new Cosh(parameter[0]).evaluateElementary()
        ).evaluateElementary();
    }

    public Generic evaluateSimplify() {
        if(parameter[0].signum()<0) {
            return new Tanh(parameter[0].negate()).evaluate().negate();
        } else if(parameter[0].signum()==0) {
            return JsclInteger.valueOf(0);
        }
        try {
            Variable v=parameter[0].variableValue();
            if(v instanceof Atanh) {
                Generic g[]=((Atanh)v).parameters();
                return g[0];
            }
        } catch (NotVariableException e) {}
        return identity();
    }

    public Generic identity(Generic a, Generic b) {
        Generic ta=new Tanh(a).evaluateSimplify();
        Generic tb=new Tanh(b).evaluateSimplify();
        return new Frac(
            ta.add(tb),
            JsclInteger.valueOf(1).add(
                ta.multiply(tb)
            )
        ).evaluateSimplify();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper)parameter[0]).tanh();
    }

    public Variable newInstance() {
        return new Tanh(null);
    }
}
