package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.NumericWrapper;
import jscl.math.TimeDependent;
import jscl.math.Variable;
import jscl.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 12/26/11
 * Time: 9:54 AM
 */
public class Rand extends Operator implements TimeDependent {

	public static final String NAME = "rand";

	public Rand() {
		super(NAME, new Generic[0]);
	}

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Rand();
	}

	@Override
	public int getMinParameters() {
		return 0;
	}

	@Override
	public Generic evaluate() {
		return new NumericWrapper(Real.valueOf(Math.random()));
	}

	@Override
	public Variable newInstance() {
		return new Rand();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public int compareTo(Object that) {
		if (this == that) return 0;
		return -1;
	}

	@Override
	public int compareTo(Variable that) {
		if (this == that) return 0;
		return -1;
	}
}
