package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;

public class Sqrt extends Algebraic {
    public Sqrt(Generic generic) {
        super("√",new Generic[] {generic});
    }

    public Root rootValue() {
        return new Root(
            new Generic[] {
                parameter[0].negate(),
                JsclInteger.valueOf(0),
                JsclInteger.valueOf(1)
            },
            0
        );
    }

    public Generic antiDerivative(Variable variable) throws NotIntegrableException {
        Root r=rootValue();
        Generic g[]=r.parameters();
        if(g[0].isPolynomial(variable)) {
            return AntiDerivative.compute(r, variable);
        } else throw new NotIntegrableException();
    }

    public Generic derivative(int n) {
        return Constant.half.multiply(
            new Inv(
                evaluate()
            ).evaluate()
        );
    }

    public boolean imaginary() {
        return parameter[0].compareTo(JsclInteger.valueOf(-1))==0;
    }

    public Generic evaluate() {
        try {
            JsclInteger en=parameter[0].integerValue();
            if(en.signum()<0);
            else {
                Generic rt=en.sqrt();
                if(rt.pow(2).compareTo(en)==0) return rt;
            }
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return evaluate();
    }

    public Generic evaluateSimplify() {
        try {
            JsclInteger en=parameter[0].integerValue();
            if(en.signum()<0) return Constant.i.multiply(new Sqrt(en.negate()).evaluateSimplify());
            else {
                Generic rt=en.sqrt();
                if(rt.pow(2).compareTo(en)==0) return rt;
            }
            Generic a=en.factorize();
            Generic p[]=a.productValue();
            Generic s= JsclInteger.valueOf(1);
            for(int i=0;i<p.length;i++) {
                Power o=p[i].powerValue();
                Generic q=o.value(true);
                int c=o.exponent();
                s=s.multiply(q.pow(c/2).multiply(new Sqrt(q).expressionValue().pow(c%2)));
            }
            return s;
        } catch (NotIntegerException e) {
            Generic n[]=Frac.separateCoefficient(parameter[0]);
            if(n[0].compareTo(JsclInteger.valueOf(1))==0 && n[1].compareTo(JsclInteger.valueOf(1))==0);
            else return new Sqrt(n[2]).evaluateSimplify().multiply(
                new Frac(
                    new Sqrt(n[0]).evaluateSimplify(),
                    new Sqrt(n[1]).evaluateSimplify()
                ).evaluateSimplify()
            );
        }
        return expressionValue();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper)parameter[0]).sqrt();
    }

    public String toJava() {
        if(parameter[0].compareTo(JsclInteger.valueOf(-1))==0) return "Complex.valueOf(0, 1)";
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameter[0].toJava());
        buffer.append(".").append(name).append("()");
        return buffer.toString();
    }

    void bodyToMathML(MathML element, boolean fenced) {
        if(parameter[0].compareTo(JsclInteger.valueOf(-1))==0) {
            MathML e1=element.element("mi");
            e1.appendChild(element.text(/*"\u2148"*/"i"));
            element.appendChild(e1);
        } else {
            MathML e1=element.element("msqrt");
            parameter[0].toMathML(e1,null);
            element.appendChild(e1);
        }
    }

    public Variable newInstance() {
        return new Sqrt(null);
    }
}
