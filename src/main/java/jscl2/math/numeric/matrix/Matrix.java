package jscl2.math.numeric.matrix;

import jscl2.math.numeric.NumericNumber;
import jscl2.math.numeric.Numeric;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.TabableView;

/**
 * User: serso
 * Date: 2/2/12
 * Time: 12:09 PM
 */
public interface Matrix<M extends Matrix<M>> {

	/**
	 * @return number of rows in matrix
	 */
	int getRows();

	/**
	 * @return number of columns in matrix
	 */
	int getCols();


	/**
	 * Method returns (row, col) element of matrix
	 *
	 * @param row row of the element
	 * @param col column of the element
	 * @return (row, col) element of matrix
	 */
	@NotNull
	NumericNumber getIJ(int row, int col);


	/*
	 * ********************************
	 * 		ARITHMETIC OPERATIONS
	 * ********************************
	 */
	@NotNull
	M add(@NotNull M that);

	@NotNull
	M subtract(@NotNull M that);

	@NotNull
	M multiply(@NotNull M that);

	@NotNull
	Numeric multiply(@NotNull Vector that);

	/**
	 * @return matrix transposed to current
	 */
	@NotNull
	M transpose();

	@NotNull
	NumericNumber trace();

	@NotNull
	NumericNumber determinant();

	/**
	 * Main interface for building new matrix
	 * NOTE: this builder provides access to the elements and this is the last point where the elements might be modified
	 * @param <T>
	 */
	public static interface Builder<T extends Matrix> {

		@NotNull
		NumericNumber getIJ(int row, int col);

		void setIJ(int row, int col, @NotNull NumericNumber value);

		@NotNull
		T build();
	}
}
