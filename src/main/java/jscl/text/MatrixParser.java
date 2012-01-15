package jscl.text;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Matrix;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MatrixParser implements Parser<Matrix> {

	public static final Parser<Matrix> parser = new MatrixParser();

	private MatrixParser() {
	}

	public Matrix parse(@NotNull Parameters p, Generic previousSumElement) throws ParseException {
		int pos0 = p.getPosition().intValue();

		final List<Generic> vectors = new ArrayList<Generic>();

		ParserUtils.tryToParse(p, pos0, '[');

		try {
			vectors.add(VectorParser.parser.parse(p, previousSumElement));
		} catch (ParseException e) {
			p.getPosition().setValue(pos0);
			throw e;
		}

		while (true) {
			try {
				vectors.add(CommaAndVector.parser.parse(p, previousSumElement));
			} catch (ParseException e) {
				break;
			}
		}

		ParserUtils.tryToParse(p, pos0, ']');

		return Matrix.frame((JsclVector[]) ArrayUtils.toArray(vectors, new JsclVector[vectors.size()])).transpose();
	}
}
