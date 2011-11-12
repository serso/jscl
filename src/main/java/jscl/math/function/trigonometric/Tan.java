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
                new Cos(parameter[0]).evaluate()
            )
        ).evaluate().negate();
    }

    public Generic derivative(int n) {
        return JsclInteger.valueOf(1).add(
            new Tan(parameter[0]).evaluate().pow(2)
        );
    }

    public Generic evaluate() {
        if(parameter[0].signum()<0) {
            return new Tan(parameter[0].negate()).evaluate().negate();
        } else if(parameter[0].signum()==0) {
            return JsclInteger.valueOf(0);
        } else if(parameter[0].compareTo(Constant.pi)==0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Frac(
            new Sin(parameter[0]).evaluateElementary(),
            new Cos(parameter[0]).evaluateElementary()
        ).evaluateElementary();
    }

    public Generic evaluateSimplify() {
        if(parameter[0].signum()<0) {
            return new Tan(parameter[0].negate()).evaluate().negate();
        } else if(parameter[0].signum()==0) {
            return JsclInteger.valueOf(0);
        } else if(parameter[0].compareTo(Constant.pi)==0) {
            return JsclInteger.valueOf(0);
        }
        try {
            Variable v=parameter[0].variableValue();
            if(v instanceof Atan) {
                Generic g[]=((Atan)v).parameters();
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
        return ((NumericWrapper)parameter[0]).tan();
    }

    public Variable newInstance() {
        return new Tan(null);
    }
}
