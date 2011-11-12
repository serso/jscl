package jscl.math.function.trigonometric;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.math.function.Constant;
import jscl.math.function.Frac;
import jscl.math.function.Lg;
import jscl.math.function.Trigonometric;

public class Tan extends Trigonometric {
    public Tan(Generic generic) {
        super("tan",new Generic[] {generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return new Lg(
            JsclInteger.valueOf(4).multiply(
                new Cos(parameters[0]).evaluate()
            )
        ).evaluate().negate();
    }

    public Generic derivative(int n) {
        return JsclInteger.valueOf(1).add(
            new Tan(parameters[0]).evaluate().pow(2)
        );
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Tan(parameters[0].negate()).evaluate().negate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(0);
        } else if(parameters[0].compareTo(Constant.pi)==0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Frac(
            new Sin(parameters[0]).evaluateElementary(),
            new Cos(parameters[0]).evaluateElementary()
        ).evaluateElementary();
    }

    public Generic evaluateSimplify() {
        if(parameters[0].signum()<0) {
            return new Tan(parameters[0].negate()).evaluate().negate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(0);
        } else if(parameters[0].compareTo(Constant.pi)==0) {
            return JsclInteger.valueOf(0);
        }
        try {
            Variable v= parameters[0].variableValue();
            if(v instanceof Atan) {
                Generic g[]=((Atan)v).getParameters();
                return g[0];
            }
        } catch (NotVariableException e) {}
        return identity();
    }

    public Generic identity(Generic a, Generic b) {
        Generic ta=new Tan(a).evaluateSimplify();
        Generic tb=new Tan(b).evaluateSimplify();
        return new Frac(
            ta.add(tb),
            JsclInteger.valueOf(1).subtract(
                ta.multiply(tb)
            )
        ).evaluateSimplify();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).tan();
    }

    public Variable newInstance() {
        return new Tan(null);
    }
}
