package jscl2.math.numeric;

import jscl2.math.Arithmetic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/20/11
 * Time: 7:58 PM
 */
public interface INumeric<T extends INumeric<T>> extends Arithmetic<T>, Comparable<T> {

	@NotNull
	T pow(int exponent);

	@NotNull
	Real abs();

	@NotNull
	Real norm();

	@NotNull
	T negate();

	int signum();

	@NotNull
	T sgn();

	@NotNull
	T ln();

	@NotNull
	T lg();

	@NotNull
	T exp();

	@NotNull
	T inverse();

	@NotNull
	T sqrt();

	@NotNull
	T nThRoot(int n);

	/*
	* ******************************************************************************************
	* <p/>
	* TRIGONOMETRIC FUNCTIONS
	* <p/>
	* *******************************************************************************************/

	@NotNull
	T sin();

	@NotNull
	T cos();

	@NotNull
	T tan();

	@NotNull
	T cot();

	/*
	 * ******************************************************************************************
	 * <p/>
	 * INVERSE TRIGONOMETRIC FUNCTIONS
	 * <p/>
	 * *******************************************************************************************/

	@NotNull
	T asin();

	@NotNull
	T acos();

	@NotNull
	T atan();

	@NotNull
	T acot();

	/*
	 * ******************************************************************************************
	 * <p/>
	 * HYPERBOLIC TRIGONOMETRIC FUNCTIONS
	 * <p/>
	 * *******************************************************************************************/

	@NotNull
	T sinh();

	@NotNull
	T cosh();

	@NotNull
	T tanh();

	@NotNull
	T coth();

	/*
	 * ******************************************************************************************
	 * <p/>
	 * INVERSE HYPERBOLIC TRIGONOMETRIC FUNCTIONS
	 * <p/>
	 * *******************************************************************************************/

	@NotNull
	T asinh();

	@NotNull
	T acosh();

	@NotNull
	T atanh();

	@NotNull
	T acoth();

	/*
	 * Method returns true only if the objects are identical (e.g. real number 0 and complex number 1+0i are not equals)
	 */
	boolean equals(Object that);


	/*
	 * Method returns true only if the objects are mathemtaically equals (e.g. real number 0 and complex number 1+0i are mathematically equals)
	 */
	boolean mathEquals(INumeric<T> that);

	Numeric conjugate();

	/*
	 * ************************************
	 *	COMPARISON OPERATIONS
	 * ************************************
	 */
	boolean more(@NotNull Numeric that);

	boolean moreOrEquals(@NotNull Numeric that);

	boolean less(@NotNull Numeric that);

	boolean lessOrEquals(@NotNull Numeric that);

	/**
	 * @return matrix transposed to current
	 */
/*	@NotNull
	T transpose();*/
}
