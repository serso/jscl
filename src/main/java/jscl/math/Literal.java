package jscl.math;

import jscl.math.function.Frac;
import jscl.math.function.Pow;
import jscl.math.polynomial.Monomial;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.utils.Converter;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Literal implements Comparable {

	private Variable variables[];
	private int powers[];
	private int degree;
	private int size;

	Literal() {
	}

	Literal(int size) {
		init(size);
	}

	public int size() {
		return size;
	}

	@NotNull
	public Variable getVariable(int i) {
		return variables[i];
	}

	public int getPower(int i) {
		return powers[i];
	}

	void init(int size) {
		variables = new Variable[size];
		powers = new int[size];
		this.size = size;
	}

	void resize(int size) {
		if (size < variables.length) {
			Variable variable[] = new Variable[size];
			int power[] = new int[size];
			System.arraycopy(this.variables, 0, variable, 0, size);
			System.arraycopy(this.powers, 0, power, 0, size);
			this.variables = variable;
			this.powers = power;
			this.size = size;
		}
	}

	public Literal multiply(Literal literal) {
		Literal l = newInstance(size + literal.size);
		int i = 0;
		int i1 = 0;
		int i2 = 0;
		Variable v1 = i1 < size ? variables[i1] : null;
		Variable v2 = i2 < literal.size ? literal.variables[i2] : null;
		while (v1 != null || v2 != null) {
			int c = v1 == null ? 1 : (v2 == null ? -1 : v1.compareTo(v2));
			if (c < 0) {
				int s = powers[i1];
				l.variables[i] = v1;
				l.powers[i] = s;
				l.degree += s;
				i++;
				i1++;
				v1 = i1 < size ? variables[i1] : null;
			} else if (c > 0) {
				int s = literal.powers[i2];
				l.variables[i] = v2;
				l.powers[i] = s;
				l.degree += s;
				i++;
				i2++;
				v2 = i2 < literal.size ? literal.variables[i2] : null;
			} else {
				int s = powers[i1] + literal.powers[i2];
				l.variables[i] = v1;
				l.powers[i] = s;
				l.degree += s;
				i++;
				i1++;
				i2++;
				v1 = i1 < size ? variables[i1] : null;
				v2 = i2 < literal.size ? literal.variables[i2] : null;
			}
		}
		l.resize(i);
		return l;
	}

	public Literal divide(Literal literal) throws ArithmeticException {
		Literal l = newInstance(size + literal.size);
		int i = 0;
		int i1 = 0;
		int i2 = 0;
		Variable v1 = i1 < size ? variables[i1] : null;
		Variable v2 = i2 < literal.size ? literal.variables[i2] : null;
		while (v1 != null || v2 != null) {
			int c = v1 == null ? 1 : (v2 == null ? -1 : v1.compareTo(v2));
			if (c < 0) {
				int s = powers[i1];
				l.variables[i] = v1;
				l.powers[i] = s;
				l.degree += s;
				i++;
				i1++;
				v1 = i1 < size ? variables[i1] : null;
			} else if (c > 0) {
				throw new NotDivisibleException();
			} else {
				int s = powers[i1] - literal.powers[i2];
				if (s < 0) throw new NotDivisibleException();
				else if (s == 0) ;
				else {
					l.variables[i] = v1;
					l.powers[i] = s;
					l.degree += s;
					i++;
				}
				i1++;
				i2++;
				v1 = i1 < size ? variables[i1] : null;
				v2 = i2 < literal.size ? literal.variables[i2] : null;
			}
		}
		l.resize(i);
		return l;
	}

	public Literal gcd(Literal literal) {
		Literal l = newInstance(Math.min(size, literal.size));
		int i = 0;
		int i1 = 0;
		int i2 = 0;
		Variable v1 = i1 < size ? variables[i1] : null;
		Variable v2 = i2 < literal.size ? literal.variables[i2] : null;
		while (v1 != null || v2 != null) {
			int c = v1 == null ? 1 : (v2 == null ? -1 : v1.compareTo(v2));
			if (c < 0) {
				i1++;
				v1 = i1 < size ? variables[i1] : null;
			} else if (c > 0) {
				i2++;
				v2 = i2 < literal.size ? literal.variables[i2] : null;
			} else {
				int s = Math.min(powers[i1], literal.powers[i2]);
				l.variables[i] = v1;
				l.powers[i] = s;
				l.degree += s;
				i++;
				i1++;
				i2++;
				v1 = i1 < size ? variables[i1] : null;
				v2 = i2 < literal.size ? literal.variables[i2] : null;
			}
		}
		l.resize(i);
		return l;
	}

	public Literal scm(@NotNull Literal that) {
		final Literal result = newInstance(this.size + that.size);
		int i = 0;

		int thisI = 0;
		int thatI = 0;

		Variable thisVariable = thisI < this.size ? this.variables[thisI] : null;
		Variable thatVariable = thatI < that.size ? that.variables[thatI] : null;

		while (thisVariable != null || thatVariable != null) {
			int c;
			if (thisVariable == null) {
				c = 1;
			} else if (thatVariable == null) {
				c = -1;
			} else {
				c = thisVariable.compareTo(thatVariable);
			}

			if (c < 0) {
				int thisPower = this.powers[thisI];

				result.variables[i] = thisVariable;
				result.powers[i] = thisPower;
				result.degree += thisPower;

				i++;
				thisI++;
				thisVariable = thisI < size ? variables[thisI] : null;
			} else if (c > 0) {
				int thatPower = that.powers[thatI];

				result.variables[i] = thatVariable;
				result.powers[i] = thatPower;
				result.degree += thatPower;

				i++;
				thatI++;
				thatVariable = thatI < that.size ? that.variables[thatI] : null;
			} else {
				int maxPower = Math.max(this.powers[thisI], that.powers[thatI]);
				result.variables[i] = thisVariable;
				result.powers[i] = maxPower;
				result.degree += maxPower;

				i++;
				thisI++;
				thatI++;

				thisVariable = thisI < this.size ? variables[thisI] : null;
				thatVariable = thatI < that.size ? that.variables[thatI] : null;
			}
		}

		result.resize(i);

		return result;
	}

	public Generic[] productValue() throws NotProductException {
		Generic a[] = new Generic[size];
		for (int i = 0; i < a.length; i++) a[i] = variables[i].expressionValue().pow(powers[i]);
		return a;
	}

	public Power powerValue() throws NotPowerException {
		if (size == 0) return new Power(JsclInteger.valueOf(1), 1);
		else if (size == 1) {
			Variable v = variables[0];
			int c = powers[0];
			return new Power(v.expressionValue(), c);
		} else throw new NotPowerException();
	}

	public Variable variableValue() throws NotVariableException {
		if (size == 0) throw new NotVariableException();
		else if (size == 1) {
			Variable v = variables[0];
			int c = powers[0];
			if (c == 1) return v;
			else throw new NotVariableException();
		} else throw new NotVariableException();
	}

	public Variable[] variables() {
		Variable va[] = new Variable[size];
		System.arraycopy(variables, 0, va, 0, size);
		return va;
	}

	public int degree() {
		return degree;
	}

	public int compareTo(@NotNull Literal that) {
		int thisI = this.size;
		int thatI = that.size;

		Variable thisVariable = thisI == 0 ? null : this.variables[--thisI];
		Variable thatVariable = thatI == 0 ? null : that.variables[--thatI];

		while (thisVariable != null || thatVariable != null) {
			int c;
			if (thisVariable == null) {
				c = -1;
			} else if (thatVariable == null) {
				c = 1;
			} else {
				c = thisVariable.compareTo(thatVariable);
			}

			if (c < 0) {
				return -1;
			} else if (c > 0) {
				return 1;
			} else {

				int thisPower = this.powers[thisI];
				int thatPower = that.powers[thatI];
				if (thisPower < thatPower) {
					return -1;
				} else if (thisPower > thatPower) {
					return 1;
				}

				thisVariable = thisI == 0 ? null : this.variables[--thisI];
				thatVariable = thatI == 0 ? null : that.variables[--thatI];
			}
		}
		return 0;
	}

	public int compareTo(Object o) {
		return compareTo((Literal) o);
	}

	public static Literal newInstance() {
		return new Literal(0);
	}

	public static Literal valueOf(Variable variable) {
		return valueOf(variable, 1);
	}

	public static Literal valueOf(Variable variable, int power) {
		Literal l = new Literal();
		l.init(variable, power);
		return l;
	}

	void init(Variable var, int pow) {
		if (pow != 0) {
			init(1);
			variables[0] = var;
			powers[0] = pow;
			degree = pow;
		} else init(0);
	}

	public static Literal valueOf(Monomial monomial) {
		Literal l = new Literal();
		l.init(monomial);
		return l;
	}

	void init(Monomial monomial) {
		Map map = new TreeMap();
		Variable unk[] = monomial.unknown();
		for (int i = 0; i < unk.length; i++) {
			int c = monomial.element(i);
			if (c > 0) map.put(unk[i], c);
		}
		init(map.size());
		Iterator it = map.entrySet().iterator();
		for (int i = 0; it.hasNext(); i++) {
			Map.Entry e = (Map.Entry) it.next();
			Variable v = (Variable) e.getKey();
			int c = (Integer) e.getValue();
			variables[i] = v;
			powers[i] = c;
			degree += c;
		}
	}

	Map<Variable, Generic> content(@NotNull Converter<Variable, Generic> c) {
		final Map<Variable, Generic> result = new TreeMap<Variable, Generic>();

		for (int i = 0; i < size; i++) {
			result.put(variables[i], c.convert(variables[i]));
		}

		return result;
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();

		if (degree == 0) {
			result.append("1");
		}

		for (int i = 0; i < size; i++) {
			if (i > 0) {
				result.append("*");
			}

			final Variable var = variables[i];
			int power = powers[i];
			if (power == 1) {
				result.append(var);
			} else {
				if (var instanceof Frac || var instanceof Pow) {
					result.append("(").append(var).append(")");
				} else {
					result.append(var);
				}
				result.append("^").append(power);
			}
		}
		return result.toString();
	}

	public String toJava() {
		StringBuilder buffer = new StringBuilder();
		if (degree == 0) buffer.append("JsclDouble.valueOf(1)");
		for (int i = 0; i < size; i++) {
			if (i > 0) buffer.append(".multiply(");
			Variable v = variables[i];
			int c = powers[i];
			buffer.append(v.toJava());
			if (c == 1) ;
			else buffer.append(".pow(").append(c).append(")");
			if (i > 0) buffer.append(")");
		}
		return buffer.toString();
	}

	public void toMathML(MathML element, @Nullable Object data) {
		if (degree == 0) {
			MathML e1 = element.element("mn");
			e1.appendChild(element.text("1"));
			element.appendChild(e1);
		}
		for (int i = 0; i < size; i++) {
			Variable v = variables[i];
			int c = powers[i];
			v.toMathML(element, c);
		}
	}

	@NotNull
	private Literal newInstance(int n) {
		return new Literal(n);
	}
}
