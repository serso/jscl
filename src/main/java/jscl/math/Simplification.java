package jscl.math;

import jscl.math.function.*;
import jscl.math.polynomial.Basis;
import jscl.math.polynomial.Monomial;
import jscl.math.polynomial.Polynomial;
import jscl.math.polynomial.UnivariatePolynomial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class Simplification {

	private final Map<Variable, Generic> cache = new TreeMap<Variable, Generic>();

	Generic result;
	private final List<Constraint> constraints = new ArrayList<Constraint>();
	boolean linear;

	private Simplification() {
	}

	public static Generic compute(@NotNull Generic generic) {
		final Simplification s = new Simplification();
		s.computeValue(generic);
		return s.getValue();
	}

	void computeValue(Generic generic) {
		Debug.println("simplification");
		Debug.increment();

		final Variable t = new TechnicalVariable("t");
		linear = false;
		process(new Constraint(t, t.expressionValue().subtract(generic), false));
		UnivariatePolynomial p = polynomial(t);

		switch (p.degree()) {
			case 0:
				result = generic;
				break;
			case 1:
				result = new Root(p, 0).evaluateSimplify();
				break;
//          case 2:
//              int n=branch(generic,p);
//              if(n<p.degree()) linear(new Root(p,n).expressionValue());
//              else linear(generic);
//              break;
			default:
				linear(generic);
		}

		Debug.decrement();
	}

	void linear(Generic generic) {
		Variable t = new TechnicalVariable("t");
		linear = true;
		constraints.clear();
		process(new Constraint(t, t.expressionValue().subtract(generic), false));
		UnivariatePolynomial p = polynomial(t);
		switch (p.degree()) {
			case 0:
				result = generic;
				break;
			default:
				result = new Root(p, 0).evaluateSimplify();
		}
	}

	int branch(Generic generic, UnivariatePolynomial polynomial) {
		int n = polynomial.degree();
		Variable t = new TechnicalVariable("t");
		linear = true;
		for (int i = 0; i < n; i++) {
			constraints.clear();
			process(new Constraint(t, t.expressionValue().subtract(generic.subtract(new Root(polynomial, i).expressionValue())), false));
			Generic a = polynomial(t).solve();
			if (a != null ? a.signum() == 0 : false) return i;
		}
		return n;
	}

	UnivariatePolynomial polynomial(Variable t) {
		Polynomial fact = Polynomial.factory(t);
		int n = constraints.size();
		Generic a[] = new Generic[n];
		Variable unk[] = new Variable[n];
		if (linear) {
			int j = 0;
			for (Constraint constraint : constraints) {
				if (constraint.reduce) {
					a[j] = constraint.generic;
					unk[j] = constraint.unknown;
					j++;
				}
			}
			int k = 0;
			for (Constraint c : constraints) {
				if (!c.reduce) {
					a[j] = c.generic;
					unk[j] = c.unknown;
					j++;
					k++;
				}
			}
			a = solve(a, unk, k);
			for (Generic anA : a) {
				UnivariatePolynomial p = (UnivariatePolynomial) fact.valueOf(anA);
				if (p.degree() == 1) return p;
			}
			return null;
		} else {
			for (int i = 0; i < n; i++) {
				Constraint c = constraints.get(i);
				a[i] = c.generic;
				unk[i] = c.unknown;
			}
			a = solve(a, unk, n);
			return (UnivariatePolynomial) fact.valueOf(a[0]);
		}
	}

	Generic[] solve(Generic generic[], Variable unknown[], int n) {
		Variable unk[] = Basis.augmentUnknown(unknown, generic);
		return Basis.compute(generic, unk, Monomial.kthElimination(n)).elements();
	}

	void process(Constraint co) {
		int n1 = 0;
		int n2 = 0;
		constraints.add(co);
		do {
			n1 = n2;
			n2 = constraints.size();
			for (int i = n1; i < n2; i++) {
				co = constraints.get(i);
				subProcess(co);
			}
		} while (n1 < n2);
	}

	void subProcess(Constraint co) {
		Variable va[] = co.generic.variables();
		for (Variable v : va) {
			if (constraints.contains(new Constraint(v))) continue;
			co = null;
			if (v instanceof Frac) {
				Generic g[] = ((Frac) v).getParameters();
				co = new Constraint(v, v.expressionValue().multiply(g[1]).subtract(g[0]), false);
			} else if (v instanceof Sqrt) {
				Generic g[] = ((Sqrt) v).getParameters();
				if (linear) co = linearConstraint(v);
				if (co == null) co = new Constraint(v, v.expressionValue().pow(2).subtract(g[0]), true);
			} else if (v instanceof Cubic) {
				Generic g[] = ((Cubic) v).getParameters();
				if (linear) co = linearConstraint(v);
				if (co == null) co = new Constraint(v, v.expressionValue().pow(3).subtract(g[0]), true);
			} else if (v instanceof Pow) {
				try {
					Root r = ((Pow) v).rootValue();
					int d = r.degree();
					Generic g[] = r.getParameters();
					if (linear) co = linearConstraint(v);
					if (co == null) co = new Constraint(v, v.expressionValue().pow(d).subtract(g[0].negate()), d > 1);
				} catch (NotRootException e) {
					co = linearConstraint(v);
				}
			} else if (v instanceof Root) {
				try {
					Root r = (Root) v;
					int d = r.degree();
					int n = r.subscript().integerValue().intValue();
					Generic g[] = r.getParameters();
					if (linear) co = linearConstraint(v);
					if (co == null)
						co = new Constraint(v, Root.sigma(g, d - n).multiply(JsclInteger.valueOf(-1).pow(d - n)).multiply(g[d]).subtract(g[n]), d > 1);
				} catch (NotIntegerException e) {
					co = linearConstraint(v);
				}
			} else co = linearConstraint(v);
			if (co != null) constraints.add(co);
		}
	}

	@Nullable
	private Constraint linearConstraint(@NotNull Variable v) {
		Generic s = cache.get(v);
		if (s == null) {
			s = v.simplify();
			cache.put(v, s);
		}

		Generic a = v.expressionValue().subtract(s);
		if (a.signum() != 0) {
			return new Constraint(v, a, false);
		} else {
			return null;
		}
	}

	Generic getValue() {
		return result;
	}
}

class Constraint {
	Variable unknown;
	Generic generic;
	boolean reduce;

	Constraint(Variable unknown, Generic generic, boolean reduce) {
		this.unknown = unknown;
		this.generic = generic;
		this.reduce = reduce;
	}

	Constraint(Variable unknown) {
		this(unknown, null, false);
	}

	public boolean equals(Object obj) {
		return unknown.compareTo(((Constraint) obj).unknown) == 0;
	}
}
