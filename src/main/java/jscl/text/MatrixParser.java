package jscl.text;

import java.util.ArrayList;
import java.util.List;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Matrix;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

public class MatrixParser implements Parser<Matrix> {

	public static final Parser<Matrix> parser = new MatrixParser();

	private MatrixParser() {
	}

	public Matrix parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final List<Generic> vectors = new ArrayList<Generic>();

		ParserUtils.tryToParse(expression, position, pos0, '{');

		try {
			vectors.add(VectorParser.parser.parse(expression, position, previousSumElement));
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		while (true) {
			try {
				vectors.add(CommaAndVector.parser.parse(expression, position, previousSumElement));
			} catch (ParseException e) {
				break;
			}
		}

		ParserUtils.tryToParse(expression, position, pos0, '}');

		return Matrix.frame((JsclVector[])ArrayUtils.toArray(vectors, new JsclVector[vectors.size()])).transpose();
	}
}
