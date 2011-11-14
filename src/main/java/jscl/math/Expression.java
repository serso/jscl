package jscl.math;

import jscl.math.function.Frac;
import jscl.math.function.Inv;
import jscl.math.polynomial.Polynomial;
import jscl.math.polynomial.UnivariatePolynomial;
import jscl.mathml.MathML;
import jscl.text.ExpressionParser;
import jscl.text.MutableInt;
import jscl.text.ParseException;
import jscl.text.ParserUtils;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.utils.Converter;

import java.util.*;

public class Expression extends Generic {
	Literal literal[];
	JsclInteger coef[];
	int size;

	Expression() {
	}

	Expression(int size) {
		init(size);
	}

	public int size() {
		return size;
	}

	public Literal literal(int n) {
		return literal[n];
	}

	public JsclInteger coef(int n) {
		return coef[n];
	}

	void init(int size) {
		literal = new Literal[size];
		coef = new JsclInteger[size];
		this.size = size;
	}

	void resize(int size) {
		int length = literal.length;
		if (size < length) {
			Literal literal[] = new Literal[size];
			JsclInteger coef[] = new JsclInteger[size];
			System.arraycopy(this.literal, length - size, literal, 0, size);
			System.arraycopy(this.coef, length - size, coef, 0, size);
			this.literal = literal;
			this.coef = coef;
			this.size = size;
		}
	}

	public Expression add(Expression expression) {
		Expression ex = newInstance(size + expression.size);
		int i = ex.size;
		int i1 = size;
		int i2 = expression.size;
		Literal l1 = i1 > 0 ? literal[--i1] : null;
		Literal l2 = i2 > 0 ? expression.literal[--i2] : null;
		while (l1 != null || l2 != null) {
			int c = l1 == null ? 1 : (l2 == null ? -1 : -l1.compareTo(l2));
			if (c < 0) {
				JsclInteger en = coef[i1];
				--i;
				ex.literal[i] = l1;
				ex.coef[i] = en;
				l1 = i1 > 0 ? literal[--i1] : null;
			} else if (c > 0) {
				JsclInteger en = expression.coef[i2];
				--i;
				ex.literal[i] = l2;
				ex.coef[i] = en;
				l2 = i2 > 0 ? expression.literal[--i2] : null;
			} else {
				JsclInteger en = coef[i1].add(expression.coef[i2]);
				if (en.signum() != 0) {
					--i;
					ex.literal[i] = l1;
					ex.coef[i] = en;
				}
				l1 = i1 > 0 ? literal[--i1] : null;
				l2 = i2 > 0 ? expression.literal[--i2] : null;
			}
		}
		ex.resize(ex.size - i);
		return ex;
	}

	public Generic add(Generic generic) {
		if (generic instanceof Expression) {
			return add((Expression) generic);
		} else if (generic instanceof JsclInteger || generic instanceof Rational) {
			return add(valueOf(generic));
		} else {
			return generic.valueOf(this).add(generic);
		}
	}

	public Expression subtract(Expression expression) {
		return multiplyAndAdd(Literal.valueOf(), JsclInteger.valueOf(-1), expression);
	}

	public Generic subtract(Generic generic) {
		if (generic instanceof Expression) {
			return subtract((Expression) generic);
		} else if (generic instanceof JsclInteger || generic instanceof Rational) {
			return subtract(valueOf(generic));
		} else {
			return generic.valueOf(this).subtract(generic);
		}
	}

	Expression multiplyAndAdd(Literal lit, JsclInteger integer, Expression expression) {
		if (integer.signum() == 0) return this;
		Expression ex = newInstance(size + expression.size);
		int i = ex.size;
		int i1 = size;
		int i2 = expression.size;
		Literal l1 = i1 > 0 ? literal[--i1] : null;
		Literal l2 = i2 > 0 ? expression.literal[--i2].multiply(lit) : null;
		while (l1 != null || l2 != null) {
			int c = l1 == null ? 1 : (l2 == null ? -1 : -l1.compareTo(l2));
			if (c < 0) {
				JsclInteger en = coef[i1];
				--i;
				ex.literal[i] = l1;
				ex.coef[i] = en;
				l1 = i1 > 0 ? literal[--i1] : null;
			} else if (c > 0) {
				JsclInteger en = expression.coef[i2].multiply(integer);
				--i;
				ex.literal[i] = l2;
				ex.coef[i] = en;
				l2 = i2 > 0 ? expression.literal[--i2].multiply(lit) : null;
			} else {
				JsclInteger en = coef[i1].add(expression.coef[i2].multiply(integer));
				if (en.signum() != 0) {
					--i;
					ex.literal[i] = l1;
					ex.coef[i] = en;
				}
				l1 = i1 > 0 ? literal[--i1] : null;
				l2 = i2 > 0 ? expression.literal[--i2].multiply(lit) : null;
			}
		}
		ex.resize(ex.size - i);
		return ex;
	}

	public Expression multiply(Expression expression) {
		Expression ex = newInstance(0);
		for (int i = 0; i < size; i++) ex = ex.multiplyAndAdd(literal[i], coef[i], expression);
		return ex;
	}

	public Generic multiply(Generic generic) {
		if (generic instanceof Expression) {
			return multiply((Expression) generic);
		} else if (generic instanceof JsclInteger) {
			return multiply(valueOf(generic));
		} else if (generic instanceof Rational) {
			return multiply(valueOf(generic));
		} else {
			return generic.multiply(this);
		}
	}

	public Generic divide(Generic generic) throws ArithmeticException {
		Generic a[] = divideAndRemainder(generic);
		if (a[1].signum() == 0) return a[0];
		else throw new NotDivisibleException();
	}

	public Generic[] divideAndRemainder(Generic generic) throws ArithmeticException {
		if (generic instanceof Expression) {
			Expression ex = (Expression) generic;
			Literal l1 = literalScm();
			Literal l2 = ex.literalScm();
			Literal l = l1.gcd(l2);
			Variable va[] = l.variables();
			if (va.length == 0) {
				if (signum() == 0 && ex.signum() != 0) return new Generic[]{this, JsclInteger.valueOf(0)};
				else try {
					return divideAndRemainder(ex.integerValue());
				} catch (NotIntegerException e) {
					return new Generic[]{JsclInteger.valueOf(0), this};
				}
			} else {
				Polynomial fact = Polynomial.factory(va[0]);
				Polynomial p[] = fact.valueof(this).divideAndRemainder(fact.valueof(ex));
				return new Generic[]{p[0].genericValue(), p[1].genericValue()};
			}
		} else if (generic instanceof JsclInteger) {
			try {
				Expression ex = newInstance(size);
				for (int i = 0; i < size; i++) {
					ex.literal[i] = literal[i];
					ex.coef[i] = coef[i].divide((JsclInteger) generic);
				}
				return new Generic[]{ex, JsclInteger.valueOf(0)};
			} catch (NotDivisibleException e) {
				return new Generic[]{JsclInteger.valueOf(0), this};
			}
		} else if (generic instanceof Rational) {
			return divideAndRemainder(valueOf(generic));
		} else {
			return generic.valueOf(this).divideAndRemainder(generic);
		}
	}

	public Generic gcd(Generic generic) {
		if (generic instanceof Expression) {
			Expression ex = (Expression) generic;
			Literal l1 = literalScm();
			Literal l2 = ex.literalScm();
			Literal l = l1.gcd(l2);
			Variable va[] = l.variables();
			if (va.length == 0) {
				if (signum() == 0) return ex;
				else return gcd(ex.gcd());
			} else {
				Polynomial fact = Polynomial.factory(va[0]);
				return fact.valueof(this).gcd(fact.valueof(ex)).genericValue();
			}
		} else if (generic instanceof JsclInteger) {
			if (generic.signum() == 0) return this;
			else return gcd().gcd(generic);
		} else if (generic instanceof Rational) {
			return gcd(valueOf(generic));
		} else {
			return generic.valueOf(this).gcd(generic);
		}
	}

	public Generic gcd() {
		JsclInteger en = JsclInteger.valueOf(0);
		for (int i = size - 1; i >= 0; i--) en = en.gcd(coef[i]);
		return en;
	}

	public Literal literalScm() {
		Literal result = Literal.valueOf();
		for (int i = 0; i < size; i++) {
			result = result.scm(literal[i]);
		}
		return result;
	}

	public Generic negate() {
		return multiply(JsclInteger.valueOf(-1));
	}

	public int signum() {
		return size == 0 ? 0 : coef[0].signum();
	}

	public int degree() {
		return 0;
	}

	public Generic antiDerivative(Variable variable) throws NotIntegrableException {
		if (isPolynomial(variable)) {
			return ((UnivariatePolynomial) Polynomial.factory(variable).valueof(this)).antiderivative().genericValue();
		} else {
			try {
				Variable v = variableValue();
				try {
					return v.antiDerivative(variable);
				} catch (NotIntegrableException e) {
					if (v instanceof Frac) {
						Generic g[] = ((Frac) v).getParameters();
						if (g[1].isConstant(variable)) {
							return new Inv(g[1]).evaluate().multiply(g[0].antiDerivative(variable));
						}
					}
				}
			} catch (NotVariableException e) {
				Generic sumElements[] = sumValue();
				if (sumElements.length > 1) {

					Generic result = JsclInteger.valueOf(0);
					for (Generic sumElement : sumElements) {
						result = result.add(sumElement.antiDerivative(variable));
					}
					return result;

				} else {
					final Generic products[] = sumElements[0].productValue();
					Generic constantProduct = JsclInteger.valueOf(1);
					Generic notConstantProduct = JsclInteger.valueOf(1);
					for (Generic product : products) {
						if (product.isConstant(variable)) {
							constantProduct = constantProduct.multiply(product);
						} else {
							notConstantProduct = notConstantProduct.multiply(product);
						}
					}
					if (constantProduct.compareTo(JsclInteger.valueOf(1)) != 0) {
						return constantProduct.multiply(notConstantProduct.antiDerivative(variable));
					}
				}
			}
		}
		throw new NotIntegrableException();
	}

	public Generic derivative(Variable variable) {
		Generic s = JsclInteger.valueOf(0);
		Literal l = literalScm();
		int n = l.size;
		for (int i = 0; i < n; i++) {
			Variable v = l.variable[i];
			Generic a = ((UnivariatePolynomial) Polynomial.factory(v).valueof(this)).derivative(variable).genericValue();
			s = s.add(a);
		}
		return s;
	}

	public Generic substitute(Variable variable, Generic generic) {
		Map m = literalScm().content();
		for (Object o : m.entrySet()) {
			Map.Entry e = (Map.Entry) o;
			Variable v = (Variable) e.getKey();
			e.setValue(v.substitute(variable, generic));
		}
		return substitute(m);
	}

	Generic substitute(Map map) {
		Generic s = JsclInteger.valueOf(0);
		for (int i = 0; i < size; i++) {
			Literal l = literal[i];
			Generic a = coef[i];
			int m = l.size;
			for (int j = 0; j < m; j++) {
				Variable v = l.variable[j];
				int c = l.power[j];
				Generic b = (Generic) map.get(v);
				b = b.pow(c);
				if (Matrix.product(a, b)) throw new ArithmeticException();
				a = a.multiply(b);
			}
			s = s.add(a);
		}
		return s;
	}

	public Generic expand() {
		return substitute(transform(literalScm().content(), new Converter<Variable, Generic>() {
			@NotNull
			@Override
			public Generic convert(@NotNull Variable variable) {
				return variable.expand();
			}
		}));
	}

	@NotNull
	private Map transform(@NotNull Map m, @NotNull Converter<Variable, Generic> converter) {
		// WE MUST USE THE PASSED MAP (in other cases - some problems were found, see tests)
		for (Map.Entry e : ((Map<Object, Object>) m).entrySet()) {
			e.setValue(converter.convert(((Variable) e.getKey())));
		}
		return m;
	}

	public Generic factorize() {
		return Factorization.compute(substitute(transform(literalScm().content(), new Converter<Variable, Generic>() {
			@NotNull
			@Override
			public Generic convert(@NotNull Variable variable) {
				return variable.factorize();
			}
		})));
	}

	public Generic elementary() {
		return substitute(transform(literalScm().content(), new Converter<Variable, Generic>() {
			@NotNull
			@Override
			public Generic convert(@NotNull Variable variable) {
				return variable.elementary();
			}
		}));
	}

	public Generic simplify() {
		return Simplification.compute(this);
	}

	public Generic numeric() {
		try {
			return integerValue().numeric();
		} catch (NotIntegerException ex) {
			return substitute(transform(literalScm().content(), new Converter<Variable, Generic>() {
				@NotNull
				@Override
				public Generic convert(@NotNull Variable variable) {
					return variable.numeric();
				}
			}));
		}
	}

	public Generic valueOf(Generic generic) {
		Expression ex = newInstance(0);
		ex.init(generic);
		return ex;
	}

	@NotNull
	public Generic[] sumValue() {
		final Generic result[] = new Generic[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = valueOf(literal[i], coef[i]);
		}
		return result;
	}

	@NotNull
	public Generic[] productValue() throws NotProductException {
		if (size == 0) {
			return new Generic[]{JsclInteger.valueOf(0)};
		} else if (size == 1) {
			final Literal l = literal[0];
			final JsclInteger k = coef[0];

			Generic productElements[] = l.productValue();
			if (k.compareTo(JsclInteger.valueOf(1)) == 0) {
				return productElements;
			} else {
				final Generic result[] = new Generic[productElements.length + 1];
				System.arraycopy(productElements, 0, result, 1, productElements.length);
				result[0] = k;
				return result;
			}
		} else {
			throw new NotProductException();
		}
	}

	public Power powerValue() throws NotPowerException {
		if (size == 0) return new Power(JsclInteger.valueOf(0), 1);
		else if (size == 1) {
			Literal l = literal[0];
			JsclInteger en = coef[0];
			if (en.compareTo(JsclInteger.valueOf(1)) == 0) return l.powerValue();
			else if (l.degree() == 0) return en.powerValue();
			else throw new NotPowerException();
		} else throw new NotPowerException();
	}

	public Expression expressionValue() throws NotExpressionException {
		return this;
	}

	@Override
	public boolean isInteger() {
		try {
			integerValue();
			return true;
		} catch (NotIntegerException e) {
			return false;
		}
	}

	public JsclInteger integerValue() throws NotIntegerException {
		if (size == 0) {
			return JsclInteger.valueOf(0);
		} else if (size == 1) {
			Literal l = literal[0];
			JsclInteger en = coef[0];
			if (l.degree() == 0) {
				return en;
			} else {
				throw new NotIntegerException();
			}
		} else {
			throw new NotIntegerException();
		}
	}

	public Variable variableValue() throws NotVariableException {
		if (size == 0) throw new NotVariableException();
		else if (size == 1) {
			Literal l = literal[0];
			JsclInteger en = coef[0];
			if (en.compareTo(JsclInteger.valueOf(1)) == 0) return l.variableValue();
			else throw new NotVariableException();
		} else throw new NotVariableException();
	}

	public Variable[] variables() {
		return literalScm().variables();
	}

	public static Variable[] variables(Generic elements[]) {
		final List<Variable> result = new ArrayList<Variable>();

		for (Generic element : elements) {
			for (Variable variable : element.variables()) {
				if (!result.contains(variable)) {
					result.add(variable);
				}
			}
		}

		return ArrayUtils.toArray(result, new Variable[result.size()]);
	}

	public boolean isPolynomial(Variable variable) {
		boolean s = true;
		Literal l = literalScm();
		int n = l.size;
		for (int i = 0; i < n; i++) {
			Variable v = l.variable[i];
			s = s && (v.isConstant(variable) || v.isIdentity(variable));
		}
		return s;
	}

	public boolean isConstant(Variable variable) {
		boolean s = true;
		Literal l = literalScm();
		int n = l.size;
		for (int i = 0; i < n; i++) {
			Variable v = l.variable[i];
			s = s && v.isConstant(variable);
		}
		return s;
	}

	public JsclVector grad(Variable variable[]) {
		Generic v[] = new Generic[variable.length];
		for (int i = 0; i < variable.length; i++) v[i] = derivative(variable[i]);
		return new JsclVector(v);
	}

	public Generic laplacian(Variable variable[]) {
		return grad(variable).divergence(variable);
	}

	public Generic dalembertian(Variable variable[]) {
		Generic a = derivative(variable[0]).derivative(variable[0]);
		for (int i = 1; i < 4; i++) a = a.subtract(derivative(variable[i]).derivative(variable[i]));
		return a;
	}

	public int compareTo(Expression expression) {
		int i1 = size;
		int i2 = expression.size;
		Literal l1 = i1 == 0 ? null : literal[--i1];
		Literal l2 = i2 == 0 ? null : expression.literal[--i2];
		while (l1 != null || l2 != null) {
			int c = l1 == null ? -1 : (l2 == null ? 1 : l1.compareTo(l2));
			if (c < 0) return -1;
			else if (c > 0) return 1;
			else {
				c = coef[i1].compareTo(expression.coef[i2]);
				if (c < 0) return -1;
				else if (c > 0) return 1;
				l1 = i1 == 0 ? null : literal[--i1];
				l2 = i2 == 0 ? null : expression.literal[--i2];
			}
		}
		return 0;
	}

	public int compareTo(Generic generic) {
		if (generic instanceof Expression) {
			return compareTo((Expression) generic);
		} else if (generic instanceof JsclInteger || generic instanceof Rational) {
			return compareTo(valueOf(generic));
		} else {
			return generic.valueOf(this).compareTo(generic);
		}
	}

	public static Expression valueOf(Variable variable) {
		return valueOf(Literal.valueOf(variable));
	}

	public static Expression valueOf(Literal literal) {
		return valueOf(literal, JsclInteger.valueOf(1));
	}

	public static Expression valueOf(JsclInteger integer) {
		return valueOf(Literal.valueOf(), integer);
	}

	public static Expression valueOf(Literal literal, JsclInteger integer) {
		Expression ex = new Expression();
		ex.init(literal, integer);
		return ex;
	}

	void init(Literal lit, JsclInteger integer) {
		if (integer.signum() != 0) {
			init(1);
			literal[0] = lit;
			coef[0] = integer;
		} else init(0);
	}

	public static Expression valueOf(Rational rational) {
		Expression ex = new Expression();
		ex.init(rational);
		return ex;
	}

	public static Expression valueOf(@NotNull String expression) throws ParseException {
		final MutableInt position = new MutableInt(0);

		final Generic generic = ExpressionParser.parser.parse(expression, position, 0, null);

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length()) {
			throw new ParseException();
		}

		return new Expression().init(generic);
	}

	void init(Expression expression) {
		init(expression.size);
		System.arraycopy(expression.literal, 0, literal, 0, size);
		System.arraycopy(expression.coef, 0, coef, 0, size);
	}

	void init(JsclInteger integer) {
		init(Literal.valueOf(), integer);
	}

	void init(Rational rational) {
		try {
			init(Literal.valueOf(), rational.integerValue());
		} catch (NotIntegerException e) {
			init(Literal.valueOf(rational.variableValue()), JsclInteger.valueOf(1));
		}
	}

	Expression init(Generic generic) {
		if (generic instanceof Expression) {
			init((Expression) generic);
		} else if (generic instanceof JsclInteger) {
			init((JsclInteger) generic);
		} else if (generic instanceof Rational) {
			init((Rational) generic);
		} else throw new ArithmeticException();

		return this;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		if (signum() == 0) buffer.append("0");
		for (int i = 0; i < size; i++) {
			Literal l = literal[i];
			JsclInteger en = coef[i];
			if (en.signum() > 0 && i > 0) buffer.append("+");
			if (l.degree() == 0) buffer.append(en);
			else {
				if (en.abs().compareTo(JsclInteger.valueOf(1)) == 0) {
					if (en.signum() < 0) buffer.append("-");
				} else buffer.append(en).append("*");
				buffer.append(l);
			}
		}
		return buffer.toString();
	}

	public String toJava() {
		final StringBuilder result = new StringBuilder();
		if (signum() == 0) {
			result.append("JsclDouble.valueOf(0)");
		}

		for (int i = 0; i < size; i++) {
			Literal l = literal[i];
			JsclInteger en = coef[i];
			if (i > 0) {
				if (en.signum() < 0) {
					result.append(".subtract(");
					en = (JsclInteger) en.negate();
				} else result.append(".add(");
			}
			if (l.degree() == 0) result.append(en.toJava());
			else {
				if (en.abs().compareTo(JsclInteger.valueOf(1)) == 0) {
					if (en.signum() > 0) result.append(l.toJava());
					else if (en.signum() < 0) result.append(l.toJava()).append(".negate()");
				} else result.append(en.toJava()).append(".multiply(").append(l.toJava()).append(")");
			}
			if (i > 0) result.append(")");
		}

		return result.toString();
	}

	public void toMathML(MathML element, @Nullable Object data) {
		MathML e1 = element.element("mrow");
		if (signum() == 0) {
			MathML e2 = element.element("mn");
			e2.appendChild(element.text("0"));
			e1.appendChild(e2);
		}
		for (int i = 0; i < size; i++) {
			Literal l = literal[i];
			JsclInteger en = coef[i];
			if (en.signum() > 0 && i > 0) {
				MathML e2 = element.element("mo");
				e2.appendChild(element.text("+"));
				e1.appendChild(e2);
			}
			if (l.degree() == 0) separateSign(e1, en);
			else {
				if (en.abs().compareTo(JsclInteger.valueOf(1)) == 0) {
					if (en.signum() < 0) {
						MathML e2 = element.element("mo");
						e2.appendChild(element.text("-"));
						e1.appendChild(e2);
					}
				} else separateSign(e1, en);
				l.toMathML(e1, null);
			}
		}
		element.appendChild(e1);
	}

	public static void separateSign(MathML element, Generic generic) {
		if (generic.signum() < 0) {
			MathML e1 = element.element("mo");
			e1.appendChild(element.text("-"));
			element.appendChild(e1);
			generic.negate().toMathML(element, null);
		} else {
			generic.toMathML(element, null);
		}
	}

	protected Expression newInstance(int n) {
		return new Expression(n);
	}
}
