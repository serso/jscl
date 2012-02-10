package jscl2.math.numeric.matrix;

import jscl2.math.numeric.AbstractNumber;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/9/12
 * Time: 6:08 PM
 */
public interface Vector {

	@NotNull
	AbstractNumber getI(int i);

	/*
	 * ********************************
	 * 		ARITHMETIC OPERATIONS
	 * ********************************
	 */
	@NotNull
	Vector add(@NotNull Vector that);

	@NotNull
	Vector subtract(@NotNull Vector that);

	@NotNull
	AbstractNumber multiply(@NotNull Vector that);

	boolean isTransposed();

	int getLength();

	/**
	 * Main interface for building new vector
	 * NOTE: this builder provides access to the elements and this is the last point where the elements might be modified
	 *
	 * @param <T>
	 */
	public static interface Builder<T extends Vector> {

		@NotNull
		AbstractNumber getI(int i);

		void setI(int i, @NotNull AbstractNumber value);

		@NotNull
		T build();
	}
}
