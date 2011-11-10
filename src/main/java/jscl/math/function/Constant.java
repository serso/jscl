package jscl.math.function;

import jscl.math.*;
import jscl.mathml.MathML;
import jscl.util.ArrayComparator;

public class Constant extends Variable {

	public static final Generic e = new Exp(JsclInteger.valueOf(1)).expressionValue();

	public static final Constant PI_CONST = new Constant("pi");
	public static final Generic pi = PI_CONST.expressionValue();

	public static final Constant INF_CONST = new Constant("infin");
	public static final Generic infinity = INF_CONST.expressionValue();

	public static final Generic i = new Sqrt(JsclInteger.valueOf(-1)).expressionValue();
	public static final Generic half = new Inv(JsclInteger.valueOf(2)).expressionValue();
	public static final Generic third = new Inv(JsclInteger.valueOf(3)).expressionValue();
	public static final Generic j = half.negate().multiply(JsclInteger.valueOf(1).subtract(i.multiply(new Sqrt(JsclInteger.valueOf(3)).expressionValue())));
	public static final Generic jbar = half.negate().multiply(JsclInteger.valueOf(1).add(i.multiply(new Sqrt(JsclInteger.valueOf(3)).expressionValue())));

	static final int PRIMECHARS = 3;
	protected int prime;
	protected Generic subscript[];

	public Constant(String name) {
		this(name, 0, new Generic[0]);
	}

	public Constant(String name, int prime, Generic subscript[]) {
		super(name);
		this.prime = prime;
		this.subscript = subscript;
	}

	public int prime() {
		return prime;
	}

	public Generic[] subscript() {
		return subscript;
	}

	public Generic antiDerivative(Variable variable) throws NotIntegrableException {
		return null;
	}

	public Generic derivative(Variable variable) {
		if (isIdentity(variable)) return JsclInteger.valueOf(1);
		else return JsclInteger.valueOf(0);
	}

	public Generic substitute(Variable variable, Generic generic) {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscript.length; i++) {
			v.subscript[i] = subscript[i].substitute(variable, generic);
		}
		if (v.isIdentity(variable)) return generic;
		else return v.expressionValue();
	}

	public Generic expand() {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscript.length; i++) {
			v.subscript[i] = subscript[i].expand();
		}
		return v.expressionValue();
	}

	public Generic factorize() {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscript.length; i++) {
			v.subscript[i] = subscript[i].factorize();
		}
		return v.expressionValue();
	}

	public Generic elementary() {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscript.length; i++) {
			v.subscript[i] = subscript[i].elementary();
		}
		return v.expressionValue();
	}

	public Generic simplify() {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscript.length; i++) {
			v.subscript[i] = subscript[i].simplify();
		}
		return v.expressionValue();
	}

	public Generic numeric() {
		return new NumericWrapper(this);
	}

	public boolean isConstant(Variable variable) {
		return !isIdentity(variable);
	}

	public int compareTo(Variable variable) {
		if (this == variable) {
			return 0;
		}

		int c = comparator.compare(this, variable);
		if (c == 0) {
			final Constant that = (Constant) variable;
			c = name.compareTo(that.name);
			if (c == 0) {
				c = ArrayComparator.comparator.compare(subscript, that.subscript);
				if (c == 0) {
					if (prime < that.prime) {
						return -1;
					} else if (prime > that.prime) {
						return 1;
					} else return 0;
				} else {
					return c;
				}
			} else {
				return c;
			}
		} else {
			return c;
		}
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(name);
		for (int i = 0; i < subscript.length; i++) {
			buffer.append("[").append(subscript[i]).append("]");
		}
		if (prime == 0) ;
		else if (prime <= PRIMECHARS) buffer.append(primechars(prime));
		else buffer.append("{").append(prime).append("}");
		return buffer.toString();
	}

	static String primechars(int n) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < n; i++) buffer.append("'");
		return buffer.toString();
	}

	public String toJava() {
		final IConstant constantFromRegistry = ConstantsRegistry.getInstance().get(getName());

		if (constantFromRegistry != null) {
			return constantFromRegistry.toJava();
		}

		final StringBuilder result = new StringBuilder();
		result.append(name);
		if (prime == 0) ;
		else if (prime <= PRIMECHARS) result.append(underscores(prime));
		else result.append("_").append(prime);
		for (int i = 0; i < subscript.length; i++) {
			result.append("[").append(subscript[i].integerValue().intValue()).append("]");
		}
		return result.toString();
	}

	static String underscores(int n) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < n; i++) buffer.append("_");
		return buffer.toString();
	}

	public void toMathML(MathML element, Object data) {
		int exponent = data instanceof Integer ? ((Integer) data).intValue() : 1;
		if (exponent == 1) bodyToMathML(element);
		else {
			MathML e1 = element.element("msup");
			bodyToMathML(e1);
			MathML e2 = element.element("mn");
			e2.appendChild(element.text(String.valueOf(exponent)));
			e1.appendChild(e2);
			element.appendChild(e1);
		}
	}

	public void bodyToMathML(MathML element) {
		if (subscript.length == 0) {
			if (prime == 0) {
				nameToMathML(element);
			} else {
				MathML e1 = element.element("msup");
				nameToMathML(e1);
				primeToMathML(e1);
				element.appendChild(e1);
			}
		} else {
			if (prime == 0) {
				MathML e1 = element.element("msub");
				nameToMathML(e1);
				MathML e2 = element.element("mrow");
				for (int i = 0; i < subscript.length; i++) {
					subscript[i].toMathML(e2, null);
				}
				e1.appendChild(e2);
				element.appendChild(e1);
			} else {
				MathML e1 = element.element("msubsup");
				nameToMathML(e1);
				MathML e2 = element.element("mrow");
				for (int i = 0; i < subscript.length; i++) {
					subscript[i].toMathML(e2, null);
				}
				e1.appendChild(e2);
				primeToMathML(e1);
				element.appendChild(e1);
			}
		}
	}

	void primeToMathML(MathML element) {
		if (prime <= PRIMECHARS) {
			primecharsToMathML(element, prime);
		} else {
			MathML e1 = element.element("mfenced");
			MathML e2 = element.element("mn");
			e2.appendChild(element.text(String.valueOf(prime)));
			e1.appendChild(e2);
			element.appendChild(e1);
		}
	}

	static void primecharsToMathML(MathML element, int n) {
		MathML e1 = element.element("mo");
		for (int i = 0; i < n; i++) e1.appendChild(element.text("\u2032"));
		element.appendChild(e1);
	}

	public Variable newInstance() {
		return new Constant(name, prime, new Generic[subscript.length]);
	}
}
