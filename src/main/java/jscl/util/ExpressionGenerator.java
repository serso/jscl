package jscl.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: serso
 * Date: 12/14/11
 * Time: 5:09 PM
 */
public class ExpressionGenerator {

	@NotNull
	private final List<String> subExpressions;

	private final int depth;

	public ExpressionGenerator(@NotNull List<String> subExpressions) {
		this(subExpressions, 10);
	}

	public ExpressionGenerator(@NotNull List<String> subExpressions, int depth) {
		this.subExpressions = new ArrayList<String>(subExpressions);
		this.depth = depth;
	}

	@NotNull
	public List<String> generate() {
		final List<StringBuilder> expressions = new ArrayList<StringBuilder>();
		for (String subExpression : subExpressions) {
		 	expressions.add(new StringBuilder(subExpression));
		}

		int i = 0;
		while ( i < depth ) {

			final String operation;

			final int operationId = (int)(Math.random() * 4d);
			switch (operationId) {
				case 0:
					operation = "+";
					break;
				case 1:
					operation = "-";
					break;
				case 2:
					operation = "*";
					break;
				case 3:
					operation = "/";
					break;
/*				case 4:
					operation = "^";
					break;*/
				default:
					operation = null;
			}

			if ( operation != null ) {
				for ( int j = 0; j < subExpressions.size(); j++ ) {
					expressions.get(j).append(operation).append(subExpressions.get(j));
				}
				i++;
			}
		}

		final List<String> result = new ArrayList<String>();
		for (StringBuilder expression : expressions) {
			result.add(expression.toString());
		}

		return result;
	}

	public static void main( String... args ) {
		final ArrayList<String> input = new ArrayList<String>();
		input.add("3");
		input.add("0x:fed");
		input.add("0b:101");
		for (String expression : new ExpressionGenerator(input, 20).generate()) {
			System.out.println(expression);
		}
	}
}
