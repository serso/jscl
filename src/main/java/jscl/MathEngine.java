package jscl;

import jscl.math.Generic;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/1/11
 * Time: 12:00 PM
 */
public interface MathEngine extends MathContext {

	@NotNull
	String evaluate(@NotNull String expression) throws ParseException;

	@NotNull
	String simplify(@NotNull String expression) throws ParseException;

	@NotNull
	String elementary(@NotNull String expression) throws ParseException;

	@NotNull
	Generic evaluateGeneric(@NotNull String expression) throws ParseException;

	@NotNull
	Generic simplifyGeneric(@NotNull String expression) throws ParseException;

	@NotNull
	Generic elementaryGeneric(@NotNull String expression) throws ParseException;
}
