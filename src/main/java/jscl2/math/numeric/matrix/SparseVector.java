package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import jscl2.math.numeric.AbstractNumber;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

public class SparseVector extends AbstractVector implements Vector {

	@NotNull
	private final AbstractNumber elements[];

	public static class Builder extends AbstractBuilder<SparseVector> {

		@NotNull
		private final AbstractNumber elements[];

		protected Builder(@NotNull MathContext mc, int length) {
			this(mc, length, false);
		}

		protected Builder(@NotNull MathContext mc, int length, final boolean transposed) {
			super(mc, length, transposed);

			this.elements = new AbstractNumber[length];
		}

		@NotNull
		@Override
		public AbstractNumber getI(int i) {
			return this.elements[i];
		}

		@Override
		public void setI(int i, @NotNull AbstractNumber value) {
			this.elements[i] = value;
		}

		@NotNull
		@Override
		public SparseVector build() {
			return new SparseVector(mc, elements, transposed);
		}
	}

	private SparseVector(@NotNull MathContext mathContext, @NotNull AbstractNumber[] elements) {
		this(mathContext, elements, false);
	}

	private SparseVector(@NotNull MathContext mathContext, @NotNull AbstractNumber elements[], boolean transposed) {
		super(mathContext, elements.length, transposed);
		this.elements = elements;
	}

	@NotNull
	public static SparseVector newInstance(@NotNull MathContext mathContext, @NotNull AbstractNumber[] elements) {
		assert elements.length > 1;
		return new SparseVector(mathContext, elements);
	}

	@NotNull
	public static SparseVector newInstance(@NotNull MathContext mathContext, @NotNull AbstractNumber[] elements, boolean transposed) {
		assert elements.length > 1;
		return new SparseVector(mathContext, elements, transposed);
	}

	@NotNull
	protected SparseVector newInstance() {
		return newInstance(new AbstractNumber[length]);
	}

	@NotNull
	protected SparseVector newInstance(@NotNull AbstractNumber element[]) {
		return new SparseVector(getMathContext(), element, transposed);
	}

	public AbstractNumber[] elements() {
		return elements;
	}

	@NotNull
	@Override
	public AbstractNumber getI(int i) {
		return elements[i];
	}

	@NotNull
	@Override
	protected Vector.Builder<? extends AbstractVector> getBuilder(int length, boolean transposed) {
		return new Builder(this.mc, length, transposed);
	}


	/*	public Numeric magnitude2() {
		return multiply(this);
	}*/

	/*	public int compareTo(Vector vector) {
		return ArrayComparator.comparator.compare(element, vector.element);
	}*/

/*	public int compareTo(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return compareTo((Vector) that);
		} else {
			return that.compareTo(this);
		}
	}*/

	public static Vector unity(@NotNull MathContext mc, int dimension) {
		SparseVector v = new SparseVector(mc, new AbstractNumber[dimension]);
		for (int i = 0; i < v.length; i++) {
			if (i == 0) v.elements[i] = Real.newInstance(mc, mc.fromLong(1L));
			else v.elements[i] = Real.newInstance(mc, mc.fromLong(0L));
		}
		return v;
	}

	@NotNull
	Numeric[] getElements() {
		return elements;
	}

	@Override
	public int compareTo(Numeric o) {
		// todo serso:
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
