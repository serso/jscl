package jscl.math;

import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class ModularInteger extends Generic implements Field {
    public static final ModularInteger booleanFactory=new ModularInteger(0,2);
    final int modulo;
    final int content;

    public ModularInteger(long content, int modulo) {
        this.modulo=modulo;
        this.content=(int)(content%modulo);
    }

    public int content() {
        return content;
    }

    public int modulo() {
        return modulo;
    }

    public ModularInteger add(ModularInteger integer) {
        return newinstance((long)content+integer.content);
    }

    @NotNull
	public Generic add(@NotNull Generic that) {
        return add((ModularInteger) that);
    }

    public ModularInteger subtract(ModularInteger integer) {
        return newinstance((long)content+(modulo-integer.content));
    }

    @NotNull
	public Generic subtract(@NotNull Generic that) {
        return subtract((ModularInteger) that);
    }

    public ModularInteger multiply(ModularInteger integer) {
        return newinstance((long)content*integer.content);
    }

    @NotNull
	public Generic multiply(@NotNull Generic that) {
        return multiply((ModularInteger) that);
    }

    @NotNull
	public Generic divide(@NotNull Generic that) throws ArithmeticException {
        return multiply(that.inverse());
    }

    public Generic inverse() {
        return newinstance(BigInteger.valueOf(content).modInverse(BigInteger.valueOf(modulo)).intValue());
    }

    public Generic gcd(Generic generic) {
        throw new UnsupportedOperationException();
    }

    public Generic gcd() {
        throw new UnsupportedOperationException();
    }

    public Generic pow(int exponent) {
        throw new UnsupportedOperationException();
    }

    public Generic negate() {
        return newinstance(modulo-content);
    }

    public int signum() {
        return content>0?1:0;
    }

    public int degree() {
        return 0;
    }

    public Generic antiDerivative(Variable variable) throws NotIntegrableException {
        throw new UnsupportedOperationException();
    }

    public Generic derivative(Variable variable) {
        throw new UnsupportedOperationException();
    }

    public Generic substitute(Variable variable, Generic generic) {
        throw new UnsupportedOperationException();
    }

    public Generic expand() {
        throw new UnsupportedOperationException();
    }

    public Generic factorize() {
        throw new UnsupportedOperationException();
    }

    public Generic elementary() {
        throw new UnsupportedOperationException();
    }

    public Generic simplify() {
        throw new UnsupportedOperationException();
    }

    public Generic numeric() {
        throw new UnsupportedOperationException();
    }

    public Generic valueOf(Generic generic) {
        if(generic instanceof ModularInteger) {
            return newinstance(((ModularInteger)generic).content);
        } else {
            return newinstance(((JsclInteger)generic).content().mod(BigInteger.valueOf(modulo)).intValue());
        }
    }

    public Generic[] sumValue() {
        throw new UnsupportedOperationException();
    }

    public Generic[] productValue() throws NotProductException {
        throw new UnsupportedOperationException();
    }

    public Power powerValue() throws NotPowerException {
        throw new UnsupportedOperationException();
    }

    public Expression expressionValue() throws NotExpressionException {
        return Expression.valueOf(integerValue());
    }

    public JsclInteger integerValue() throws NotIntegerException {
        return JsclInteger.valueOf(content);
    }

	@Override
	public boolean isInteger() {
		return true;
	}

	public Variable variableValue() throws NotVariableException {
        throw new UnsupportedOperationException();
    }

    public Variable[] variables() {
        throw new UnsupportedOperationException();
    }

    public boolean isPolynomial(Variable variable) {
        throw new UnsupportedOperationException();
    }

    public boolean isConstant(Variable variable) {
        throw new UnsupportedOperationException();
    }

    public int compareTo(ModularInteger integer) {
        return content<integer.content?-1:content>integer.content?1:0;
    }

    public int compareTo(Generic generic) {
        if(generic instanceof ModularInteger) {
            return compareTo((ModularInteger)generic);
        } else if(generic instanceof JsclInteger) {
            return compareTo(valueOf(generic));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static ModularInteger factory(int modulo) {
        return new ModularInteger(0,modulo);
    }

    public String toString() {
        return ""+content;
    }

    public String toJava() {
        throw new UnsupportedOperationException();
    }

    public void toMathML(MathML element, Object data) {
        throw new UnsupportedOperationException();
    }

    protected ModularInteger newinstance(long content) {
        return new ModularInteger(content,modulo);
    }
}
