package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 12/23/11
 * Time: 4:47 PM
 */
public class Gcd extends Operator {

	public Gcd(@NotNull Generic first, @NotNull Generic second) {
		this(new Generic[]{first, second});
	}

	public Gcd() {
		this(new Generic[2]);
	}

	private Gcd(@NotNull Generic[] parameters) {
		super("gcd", parameters);
	}


	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Gcd(parameters);
	}

	@Override
	public int getMinParameters() {
		return 2;
	}

	@Override
	public Generic selfExpand() {
		return expressionValue();
	}

	@Override
	public Generic numeric() {
		final Generic first = parameters[0];
		final Generic second = parameters[1];

		try{
			final JsclInteger firstInt = first.integerValue();
			final JsclInteger secondInt = second.integerValue();

			return firstInt.gcd(secondInt);
		} catch (NotIntegerException e) {
			// ok => continue
		}

		return first.gcd(second);
	}

	@NotNull
	@Override
	public Gcd newInstance() {
		return new Gcd();
	}
}
