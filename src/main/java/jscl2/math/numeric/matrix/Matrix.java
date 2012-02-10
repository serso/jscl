package jscl2.math.numeric.matrix;

import jscl2.math.numeric.NumericNumber;
import jscl2.math.numeric.Numeric;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/2/12
 * Time: 12:09 PM
 */
public interface Matrix<T extends Matrix<T>> {

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
	T add(@NotNull Matrix that);

	@NotNull
	T subtract(@NotNull Matrix that);

	@NotNull
	Matrix multiply(@NotNull Matrix that);

	@NotNull
	Numeric multiply(@NotNull Vector that);

	/**
	 * @return matrix transposed to current
	 */
	@NotNull
	T transpose();

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
