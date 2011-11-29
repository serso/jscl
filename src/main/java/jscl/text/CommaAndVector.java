package jscl.text;

import jscl.math.Generic;
import jscl.math.JsclVector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommaAndVector implements Parser<JsclVector> {

	public static final Parser<JsclVector> parser = new CommaAndVector();

	private CommaAndVector() {
	}

	@NotNull
	public JsclVector parse(@NotNull Parameters p, @Nullable Generic previousSumElement) throws ParseException {
		int pos0 = p.getPosition().intValue();

		ParserUtils.skipWhitespaces(p);

		ParserUtils.tryToParse(p, pos0, ',');

		return ParserUtils.parseWithRollback(VectorParser.parser, pos0, previousSumElement, p);
	}
}
