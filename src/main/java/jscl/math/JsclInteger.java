package jscl.math;

import jscl.mathml.MathML;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

public final class JsclInteger extends Generic {
    public static final JsclInteger factory=new JsclInteger(BigInteger.valueOf(0));
    final BigInteger content;

    public JsclInteger(BigInteger content) {
        this.content=content;
    }

    public BigInteger content() {
        return content;
    }

    public JsclInteger add(JsclInteger integer) {
        return new JsclInteger(content.add(integer.content));
    }

    public Generic add(Generic generic) {
        if(generic instanceof JsclInteger) {
            return add((JsclInteger)generic);
        } else {
            return generic.valueOf(this).add(generic);
        }
    }

    public JsclInteger subtract(JsclInteger integer) {
        return new JsclInteger(content.subtract(integer.content));
    }

    public Generic subtract(Generic generic) {
        if(generic instanceof JsclInteger) {
            return subtract((JsclInteger)generic);
        } else {
            return generic.valueOf(this).subtract(generic);
        }
    }

    public JsclInteger multiply(JsclInteger integer) {
        return new JsclInteger(content.multiply(integer.content));
    }

    public Generic multiply(Generic generic) {
        if(generic instanceof JsclInteger) {
            return multiply((JsclInteger)generic);
        } else {
            return generic.multiply(this);
        }
    }

    public JsclInteger divide(JsclInteger integer) throws ArithmeticException {
        JsclInteger e[]=divideAndRemainder(integer);
        if(e[1].signum()==0) return e[0];
        else throw new NotDivisibleException();
    }

    public Generic divide(Generic generic) throws ArithmeticException {
        if(generic instanceof JsclInteger) {
            return divide((JsclInteger)generic);
        } else {
            return generic.valueOf(this).divide(generic);
        }
    }

    public JsclInteger[] divideAndRemainder(JsclInteger integer) throws ArithmeticException {
        BigInteger b[]=content.divideAndRemainder(integer.content);
        return new JsclInteger[] {new JsclInteger(b[0]),new JsclInteger(b[1])};
    }

    public Generic[] divideAndRemainder(Generic generic) throws ArithmeticException {
        if(generic instanceof JsclInteger) {
            return divideAndRemainder((JsclInteger)generic);
        } else {
            return generic.valueOf(this).divideAndRemainder(generic);
        }
    }

    public JsclInteger remainder(JsclInteger integer) throws ArithmeticException {
        return new JsclInteger(content.remainder(integer.content));
    }

    public Generic remainder(Generic generic) throws ArithmeticException {
        if(generic instanceof JsclInteger) {
            return remainder((JsclInteger)generic);
        } else {
            return generic.valueOf(this).remainder(generic);
        }
    }

    public JsclInteger gcd(JsclInteger integer) {
        return new JsclInteger(content.gcd(integer.content));
    }

    public Generic gcd(Generic generic) {
        if(generic instanceof JsclInteger) {
            return gcd((JsclInteger)generic);
        } else {
            return generic.valueOf(this).gcd(generic);
        }
    }

    public Generic gcd() {
        return new JsclInteger(BigInteger.valueOf(signum()));
    }

    public Generic pow(int exponent) {
        return new JsclInteger(content.pow(exponent));
    }

    public Generic negate() {
        return new JsclInteger(content.negate());
    }

    public int signum() {
        return content.signum();
    }

    public int degree() {
        return 0;
    }

    public JsclInteger mod(JsclInteger integer) {
        return new JsclInteger(content.mod(integer.content));
    }

    public JsclInteger modPow(JsclInteger exponent, JsclInteger integer) {
        return new JsclInteger(content.modPow(exponent.content,integer.content));
    }

    public JsclInteger modInverse(JsclInteger integer) {
        return new JsclInteger(content.modInverse(integer.content));
    }

    public JsclInteger phi() {
        if(signum()==0) return this;
        Generic a=factorize();
        Generic p[]=a.productValue();
        Generic s= JsclInteger.valueOf(1);
        for(int i=0;i<p.length;i++) {
            Power o=p[i].powerValue();
            Generic q=o.value(true);
            int c=o.exponent();
            s=s.multiply(q.subtract(JsclInteger.valueOf(1)).multiply(q.pow(c-1)));
        }
        return s.integerValue();
    }

    public JsclInteger[] primitiveRoots() {
        JsclInteger phi=phi();
        Generic a=phi.factorize();
        Generic p[]=a.productValue();
        JsclInteger d[]=new JsclInteger[p.length];
        for(int i=0;i<p.length;i++) {
            d[i]=phi.divide(p[i].powerValue().value(true).integerValue());
        }
        int k=0;
        JsclInteger n=this;
        JsclInteger m= JsclInteger.valueOf(1);
        JsclInteger r[]=new JsclInteger[phi.phi().intValue()];
        while(m.compareTo(n)<0) {
            boolean b=m.gcd(n).compareTo(JsclInteger.valueOf(1))==0;
            for(int i=0;i<d.length;i++) {
                b=b && m.modPow(d[i],n).compareTo(JsclInteger.valueOf(1))>0;
            }
            if(b) r[k++]=m;
            m=m.add(JsclInteger.valueOf(1));
        }
        return k>0?r:new JsclInteger[0];
    }

    public JsclInteger sqrt() {
        return nthrt(2);
    }

    public JsclInteger nthrt(int n) {
//      return JsclInteger.valueOf((int)Math.pow((double)intValue(),1./n));
        if(signum()==0) return JsclInteger.valueOf(0);
        else if(signum()<0) {
            if(n%2==0) throw new ArithmeticException();
            else return (JsclInteger)((JsclInteger)negate()).nthrt(n).negate();
        } else {
            Generic x0;
            Generic x=this;
            do {
                x0=x;
                x=divideAndRemainder(x.pow(n-1))[0].add(x.multiply(JsclInteger.valueOf(n - 1))).divideAndRemainder(JsclInteger.valueOf(n))[0];
            } while(x.compareTo(x0)<0);
            return x0.integerValue();
        }
    }

    public Generic antiDerivative(Variable variable) throws NotIntegrableException {
        return multiply(variable.expressionValue());
    }

    public Generic derivative(Variable variable) {
        return JsclInteger.valueOf(0);
    }

    public Generic substitute(Variable variable, Generic generic) {
        return this;
    }

    public Generic expand() {
        return this;
    }

    public Generic factorize() {
        return Factorization.compute(this);
    }

    public Generic elementary() {
        return this;
    }

    public Generic simplify() {
        return this;
    }

    public Generic numeric() {
        return new NumericWrapper(this);
    }

    public Generic valueOf(Generic generic) {
        return new JsclInteger(((JsclInteger)generic).content);
    }

    public Generic[] sumValue() {
        if(content.signum()==0) return new Generic[0];
        else return new Generic[] {this};
    }

    public Generic[] productValue() throws NotProductException {
        if(content.compareTo(BigInteger.valueOf(1))==0) return new Generic[0];
        else return new Generic[] {this};
    }

    public Power powerValue() throws NotPowerException {
        if(content.signum()<0) throw new NotPowerException();
        else return new Power(this,1);
    }

    public Expression expressionValue() throws NotExpressionException {
        return Expression.valueOf(this);
    }

    public JsclInteger integerValue() throws NotIntegerException {
        return this;
    }

	@Override
	public boolean isInteger() {
		return true;
	}

	public Variable variableValue() throws NotVariableException {
        throw new NotVariableException();
    }

    public Variable[] variables() {
        return new Variable[0];
    }

    public boolean isPolynomial(Variable variable) {
        return true;
    }

    public boolean isConstant(Variable variable) {
        return true;
    }

    public int intValue() {
        return content.intValue();
    }

    public int compareTo(JsclInteger integer) {
        return content.compareTo(integer.content);
    }

    public int compareTo(Generic generic) {
        if(generic instanceof JsclInteger) {
            return compareTo((JsclInteger)generic);
        } else {
            return generic.valueOf(this).compareTo(generic);
        }
    }

    private static final JsclInteger ZERO=new JsclInteger(BigInteger.valueOf(0));
    private static final JsclInteger ONE=new JsclInteger(BigInteger.valueOf(1));

    public static JsclInteger valueOf(long val) {
        switch((int)val) {
        case 0:
            return ZERO;
        case 1:
            return ONE;
        default:
            return new JsclInteger(BigInteger.valueOf(val));
        }
    }

    public static JsclInteger valueOf(String str) {
        return new JsclInteger(new BigInteger(str));
    }

    public String toString() {
        return content.toString();
    }

    public String toJava() {
        return "JsclDouble.valueOf("+content+")";
    }

    public void toMathML(MathML element, @Nullable Object data) {
        int exponent=data instanceof Integer? (Integer) data :1;
        if(exponent==1) bodyToMathML(element);
        else {
            MathML e1=element.element("msup");
            bodyToMathML(e1);
            MathML e2=element.element("mn");
            e2.appendChild(element.text(String.valueOf(exponent)));
            e1.appendChild(e2);
            element.appendChild(e1);
        }
    }

    void bodyToMathML(MathML element) {
        MathML e1=element.element("mn");
        e1.appendChild(element.text(String.valueOf(content)));
        element.appendChild(e1);
    }
}
