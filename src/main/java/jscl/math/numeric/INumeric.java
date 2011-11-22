package jscl.math.numeric;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/20/11
 * Time: 7:58 PM
 */
public interface INumeric<T extends INumeric<T>> {

	@NotNull
	T pow(int exponent);

	@NotNull
	T abs();

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
}
