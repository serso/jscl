package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import jscl2.math.numeric.NumericNumber;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

public class DenseVector extends NumericVector implements Vector {

	@NotNull
	private final NumericNumber elements[];

	public static class Builder extends AbstractBuilder<DenseVector> {

		@NotNull
		private final NumericNumber elements[];

		protected Builder(@NotNull MathContext mc, int length) {
			this(mc, length, false);
		}

		protected Builder(@NotNull MathContext mc, int length, final boolean transposed) {
			super(mc, length, transposed);

			this.elements = new NumericNumber[length];
		}

		@NotNull
		@Override
		public NumericNumber getI(int i) {
			return this.elements[i];
		}

		@Override
		public void setI(int i, @NotNull NumericNumber value) {
			this.elements[i] = value;
		}

		@NotNull
		@Override
		public DenseVector build() {
			return new DenseVector(mc, elements, transposed);
		}
	}

	private DenseVector(@NotNull MathContext mathContext, @NotNull NumericNumber[] elements) {
		this(mathContext, elements, false);
	}

	private DenseVector(@NotNull MathContext mathContext, @NotNull NumericNumber elements[], boolean transposed) {
		super(mathContext, elements.length, transposed);
		this.elements = elements;
	}

	@NotNull
	public static DenseVector newInstance(@NotNull MathContext mathContext, @NotNull NumericNumber[] elements) {
		assert elements.length > 1;
		return new DenseVector(mathContext, elements);
	}

	@NotNull
	public static DenseVector newInstance(@NotNull MathContext mathContext, @NotNull NumericNumber[] elements, boolean transposed) {
		assert elements.length > 1;
		return new DenseVector(mathContext, elements, transposed);
	}

	@NotNull
	protected DenseVector newInstance() {
		return newInstance(new NumericNumber[length]);
	}

	@NotNull
	protected DenseVector newInstance(@NotNull NumericNumber element[]) {
		return new DenseVector(getMathContext(), element, transposed);
	}

	public NumericNumber[] elements() {
		return elements;
	}

	@NotNull
	@Override
	public NumericNumber getI(int i) {
		return elements[i];
	}

	@NotNull
	@Override
	protected Vector.Builder<? extends NumericVector> getBuilder(int length, boolean transposed) {
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
		DenseVector v = new DenseVector(mc, new NumericNumber[dimension]);
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
