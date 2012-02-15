package jscl2.math.numeric.matrix;

import jscl2.JsclMathContext;
import jscl2.math.numeric.NumericNumber;
import org.jetbrains.annotations.NotNull;

public class DenseVector extends NumericVector {

	@NotNull
	private final NumericNumber v[];

	public static class Builder extends AbstractBuilder<DenseVector> {

		@NotNull
		private final NumericNumber v[];

		protected Builder(@NotNull JsclMathContext mc, int length) {
			this(mc, length, false);
		}

		protected Builder(@NotNull JsclMathContext mc, int length, final boolean transposed) {
			super(mc, length, transposed);

			this.v = new NumericNumber[length];
		}

		@NotNull
		@Override
		public NumericNumber getI(int index) {
			return this.v[index];
		}

		@Override
		protected void setI0(int index, @NotNull NumericNumber value) {
			this.v[index] = value;
		}

		@NotNull
		@Override
		protected DenseVector build0() {
			return new DenseVector(mc, v, transposed);
		}
	}

	private DenseVector(@NotNull JsclMathContext mathContext, @NotNull NumericNumber[] v) {
		this(mathContext, v, false);
	}

	private DenseVector(@NotNull JsclMathContext mathContext, @NotNull NumericNumber v[], boolean transposed) {
		super(mathContext, v.length, transposed);
		this.v = v;
	}

	@NotNull
	public static DenseVector newInstance(@NotNull JsclMathContext mathContext, @NotNull NumericNumber[] elements) {
		assert elements.length > 1;
		return new DenseVector(mathContext, elements);
	}

	@NotNull
	public static DenseVector newInstance(@NotNull JsclMathContext mathContext, @NotNull NumericNumber[] elements, boolean transposed) {
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

	@NotNull
	@Override
	public NumericNumber getI(int index) {
		return v[index];
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

	@NotNull
	@Override
	public DenseVector transpose() {
		return new DenseVector(mc, v, !transposed);
	}
}
