package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;

public class Pow extends Algebraic {
    public Pow(Generic generic, Generic exponent) {
        super("pow",new Generic[] {generic,exponent});
    }

    public Root rootValue() throws NotRootException {
        try {
            Variable v=parameter[1].variableValue();
            if(v instanceof Inv) {
                Generic g=((Inv)v).parameter();
                try {
                    int d=g.integerValue().intValue();
                    if(d>0) {
                        Generic a[]=new Generic[d+1];
                        a[0]=parameter[0].negate();
                        for(int i=1;i<d;i++) a[i]= JsclInteger.valueOf(0);
                        a[d]= JsclInteger.valueOf(1);
                        return new Root(a,0);
                    }
                } catch (NotIntegerException e) {}
            }
        } catch (NotVariableException e) {}
        throw new NotRootException();
    }

    public Generic antiDerivative(Variable variable) throws NotIntegrableException {
        try {
            Root r=rootValue();
            Generic g[]=r.parameters();
            if(g[0].isPolynomial(variable)) {
                return AntiDerivative.compute(r, variable);
            } else throw new NotIntegrableException();
        } catch (NotRootException e) {}
        return super.antiDerivative(variable);
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        if(n==0) {
            return new Pow(parameter[0],parameter[1].add(JsclInteger.valueOf(1))).evaluate().multiply(new Inv(parameter[1].add(JsclInteger.valueOf(1))).evaluate());
        } else {
            return new Pow(parameter[0],parameter[1]).evaluate().multiply(new Inv(new Lg(parameter[0]).evaluate()).evaluate());
        }
    }

    public Generic derivative(int n) {
        if(n==0) {
            return new Pow(parameter[0],parameter[1].subtract(JsclInteger.valueOf(1))).evaluate().multiply(parameter[1]);
        } else {
            return new Pow(parameter[0],parameter[1]).evaluate().multiply(new Lg(parameter[0]).evaluate());
        }
    }

    public Generic evaluate() {
        if(parameter[0].compareTo(JsclInteger.valueOf(1))==0) {
            return JsclInteger.valueOf(1);
        }
        if(parameter[1].signum()<0) {
            return new Pow(new Inv(parameter[0]).evaluate(),parameter[1].negate()).evaluate();
        }
        try {
            int c=parameter[1].integerValue().intValue();
            return parameter[0].pow(c);
        } catch (NotIntegerException e) {}
        try {
            Root r=rootValue();
            int d=r.degree();
            Generic g[]=r.parameters();
            Generic a=g[0].negate();
            try {
                JsclInteger en=a.integerValue();
                if(en.signum()<0);
                else {
                    Generic rt=en.nthrt(d);
                    if(rt.pow(d).compareTo(en)==0) return rt;
                }
            } catch (NotIntegerException e) {}
        } catch (NotRootException e) {}
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Exp(
            new Lg(
                parameter[0]
            ).evaluateElementary().multiply(
                parameter[1]
            )
        ).evaluateElementary();
    }

    public Generic evaluateSimplify() {
        if(parameter[0].compareTo(JsclInteger.valueOf(1))==0) {
            return JsclInteger.valueOf(1);
        }
        if(parameter[1].signum()<0) {
            return new Pow(new Inv(parameter[0]).evaluateSimplify(),parameter[1].negate()).evaluateSimplify();
        }
        try {
            int c=parameter[1].integerValue().intValue();
            return parameter[0].pow(c);
        } catch (NotIntegerException e) {}
        try {
            Root r=rootValue();
            int d=r.degree();
            Generic g[]=r.parameters();
            Generic a=g[0].negate();
            try {
                JsclInteger en=a.integerValue();
                if(en.signum()<0);
                else {
                    Generic rt=en.nthrt(d);
                    if(rt.pow(d).compareTo(en)==0) return rt;
                }
            } catch (NotIntegerException e) {}
            switch(d) {
                case 2:
                    return new Sqrt(a).evaluateSimplify();
                case 3:
                case 4:
                case 6:
                    if(a.compareTo(JsclInteger.valueOf(-1))==0) return root_minus_1(d);
            }
        } catch (NotRootException e) {
            Generic n[]=Frac.separateCoefficient(parameter[1]);
            if(n[0].compareTo(JsclInteger.valueOf(1))==0 && n[1].compareTo(JsclInteger.valueOf(1))==0);
            else return new Pow(
                new Pow(
                    new Pow(
                        parameter[0],
                        n[2]
                    ).evaluateSimplify(),
                    new Inv(
                        n[1]
                    ).evaluateSimplify()
                ).evaluateSimplify(),
                n[0]
            ).evaluateSimplify();
        }
        return expressionValue();
    }

    static Generic root_minus_1(int d) {
        switch(d) {
            case 1:
                return JsclInteger.valueOf(-1);
            case 2:
                return Constant.i;
            case 3:
                return Constant.jbar.negate();
            case 4:
                return new Sqrt(Constant.half).expressionValue().multiply(JsclInteger.valueOf(1).add(Constant.i));
            case 6:
                return Constant.half.multiply(new Sqrt(JsclInteger.valueOf(3)).expressionValue().add(Constant.i));
            default:
                return null;
        }
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper)parameter[0]).pow((NumericWrapper)parameter[1]);
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        try {
            JsclInteger en=parameter[0].integerValue();
            if(en.signum()<0) buffer.append(GenericVariable.valueOf(en,true));
            else buffer.append(en);
        } catch (NotIntegerException e) {
            try {
                Variable v=parameter[0].variableValue();
                if(v instanceof Frac || v instanceof Pow) {
                    buffer.append(GenericVariable.valueOf(parameter[0]));
                } else buffer.append(v);
            } catch (NotVariableException e2) {
                try {
                    Power o=parameter[0].powerValue();
                    if(o.exponent()==1) buffer.append(o.value(true));
                    else buffer.append(GenericVariable.valueOf(parameter[0]));
                } catch (NotPowerException e3) {
                    buffer.append(GenericVariable.valueOf(parameter[0]));
                }
            }
        }
        buffer.append("^");
        try {
            JsclInteger en=parameter[1].integerValue();
            buffer.append(en);
        } catch (NotIntegerException e) {
            try {
                Variable v=parameter[1].variableValue();
                if(v instanceof Frac) {
                    buffer.append(GenericVariable.valueOf(parameter[1]));
                } else buffer.append(v);
            } catch (NotVariableException e2) {
                try {
                    parameter[1].powerValue();
                    buffer.append(parameter[1]);
                } catch (NotPowerException e3) {
                    buffer.append(GenericVariable.valueOf(parameter[1]));
                }
            }
        }
        return buffer.toString();
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameter[0].toJava());
        buffer.append(".pow(");
        buffer.append(parameter[1].toJava());
        buffer.append(")");
        return buffer.toString();
    }

    void bodyToMathML(MathML element, boolean fenced) {
        if(fenced) {
            MathML e1=element.element("mfenced");
            bodyToMathML(e1);
            element.appendChild(e1);
        } else {
            bodyToMathML(element);
        }
    }

    void bodyToMathML(MathML element) {
        MathML e1=element.element("msup");
        try {
            Variable v=parameter[0].variableValue();
            if(v instanceof Frac || v instanceof Pow || v instanceof Exp) {
                GenericVariable.valueOf(parameter[0]).toMathML(e1,null);
            } else parameter[0].toMathML(e1,null);
        } catch (NotVariableException e2) {
            try {
                Power o=parameter[0].powerValue();
                if(o.exponent()==1) o.value(true).toMathML(e1,null);
                else GenericVariable.valueOf(parameter[0]).toMathML(e1,null);
            } catch (NotPowerException e3) {
                GenericVariable.valueOf(parameter[0]).toMathML(e1,null);
            }
        }
        parameter[1].toMathML(e1,null);
        element.appendChild(e1);
    }

    public Variable newInstance() {
        return new Pow(null,null);
    }
}
