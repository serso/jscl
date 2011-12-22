package jscl.math.function;

import jscl.math.*;
import jscl.mathml.MathML;
import jscl.util.ArrayComparator;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.utils.history.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public class Constant extends Variable {

	public static final Generic e = new Exp(JsclInteger.valueOf(1)).expressionValue();

	public static final Constant PI_CONST = new Constant("π");
	public static final Constant PI_INV_CONST = new Constant("PI");
	public static final Generic pi = PI_CONST.expressionValue();

	public static final Constant I_CONST = new Constant("i");
	public static final Constant INF_CONST2 = new Constant("Infinity");
	public static final Constant INF_CONST = new Constant("∞");
	public static final Generic infinity = INF_CONST.expressionValue();

	public static final Generic i = new Sqrt(JsclInteger.valueOf(-1)).expressionValue();
	public static final Generic half = new Inv(JsclInteger.valueOf(2)).expressionValue();
	public static final Generic third = new Inv(JsclInteger.valueOf(3)).expressionValue();
	public static final Generic j = half.negate().multiply(JsclInteger.valueOf(1).subtract(i.multiply(new Sqrt(JsclInteger.valueOf(3)).expressionValue())));
	public static final Generic jbar = half.negate().multiply(JsclInteger.valueOf(1).add(i.multiply(new Sqrt(JsclInteger.valueOf(3)).expressionValue())));

	public static final int PRIME_CHARS = 3;
	private int prime;
	private Generic subscripts[];

	public Constant(String name) {
		this(name, 0, new Generic[0]);
	}

	public Constant(String name, int prime, Generic subscripts[]) {
		super(name);
		this.prime = prime;
		this.subscripts = subscripts;
	}

	public int prime() {
		return prime;
	}

	public Generic[] subscript() {
		return subscripts;
	}

	public Generic antiDerivative(Variable variable) throws NotIntegrableException {
		return null;
	}

	public Generic derivative(Variable variable) {
		if (isIdentity(variable)) {
			return JsclInteger.valueOf(1);
		} else {
			return JsclInteger.valueOf(0);
		}
	}

	public Generic substitute(Variable variable, Generic generic) {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscripts.length; i++) {
			v.subscripts[i] = subscripts[i].substitute(variable, generic);
		}

		if (v.isIdentity(variable)) {
			return generic;
		} else {
			return v.expressionValue();
		}
	}

	public Generic expand() {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscripts.length; i++) {
			v.subscripts[i] = subscripts[i].expand();
		}
		return v.expressionValue();
	}

	public Generic factorize() {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscripts.length; i++) {
			v.subscripts[i] = subscripts[i].factorize();
		}
		return v.expressionValue();
	}

	public Generic elementary() {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscripts.length; i++) {
			v.subscripts[i] = subscripts[i].elementary();
		}
		return v.expressionValue();
	}

	public Generic simplify() {
		Constant v = (Constant) newInstance();
		for (int i = 0; i < subscripts.length; i++) {
			v.subscripts[i] = subscripts[i].simplify();
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
				c = ArrayComparator.comparator.compare(this.subscripts, that.subscripts);
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

	@Override
	public int hashCode() {
		final HashCodeBuilder result = new HashCodeBuilder();

		result.append(Constant.class);
		result.append(name);
		result.append(subscripts);
		result.append(prime);

		return result.toHashCode();
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();
		result.append(name);

		for (Generic subscript : subscripts) {
			result.append("[").append(subscript).append("]");
		}

		if (prime != 0) {
			if (prime <= PRIME_CHARS) result.append(primeChars(prime));
			else result.append("{").append(prime).append("}");
		}

		return result.toString();
	}

	static String primeChars(int n) {
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

		if (prime != 0) {
			if (prime <= PRIME_CHARS) result.append(underscores(prime));
			else result.append("_").append(prime);
		}

		for (Generic subscript : subscripts) {
			result.append("[").append(subscript.integerValue().intValue()).append("]");
		}
		return result.toString();
	}

	static String underscores(int n) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < n; i++) buffer.append("_");
		return buffer.toString();
	}

	public void toMathML(MathML element, Object data) {
		int exponent = data instanceof Integer ? (Integer) data : 1;
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
		if (subscripts.length == 0) {
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
				for (int i = 0; i < subscripts.length; i++) {
					subscripts[i].toMathML(e2, null);
				}
				e1.appendChild(e2);
				element.appendChild(e1);
			} else {
				MathML e1 = element.element("msubsup");
				nameToMathML(e1);
				MathML e2 = element.element("mrow");
				for (int i = 0; i < subscripts.length; i++) {
					subscripts[i].toMathML(e2, null);
				}
				e1.appendChild(e2);
				primeToMathML(e1);
				element.appendChild(e1);
			}
		}
	}

	void primeToMathML(MathML element) {
		if (prime <= PRIME_CHARS) {
			primeCharsToMathML(element, prime);
		} else {
			MathML e1 = element.element("mfenced");
			MathML e2 = element.element("mn");
			e2.appendChild(element.text(String.valueOf(prime)));
			e1.appendChild(e2);
			element.appendChild(e1);
		}
	}

	static void primeCharsToMathML(MathML element, int n) {
		MathML e1 = element.element("mo");
		for (int i = 0; i < n; i++) e1.appendChild(element.text("\u2032"));
		element.appendChild(e1);
	}

	public Variable newInstance() {
		return new Constant(name, prime, new Generic[subscripts.length]);
	}

	@NotNull
	@Override
	public Set<? extends Constant> getConstants() {
		final Set<Constant> result = new HashSet<Constant>();
		result.add(this);
		return result;
	}
}
