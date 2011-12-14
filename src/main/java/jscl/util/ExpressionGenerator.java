package jscl.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

			final String operation = generateOperation();
			final String function = generateFunction();
			final boolean brackets = generateBrackets();

			for ( int j = 0; j < subExpressions.size(); j++ ) {
				final StringBuilder expression = expressions.get(j);
				expression.append(operation);

				if (function == null) {
					expression.append(subExpressions.get(j));
				} else {
					expression.append(function).append("(").append(subExpressions.get(j)).append(")");
				}

				if ( brackets ) {
					expressions.set(j, new StringBuilder("(").append(expression).append(")"));
				}
			}
			i++;
		}

		final List<String> result = new ArrayList<String>();
		for (StringBuilder expression : expressions) {
			result.add(expression.toString());
		}

		return result;
	}

	private boolean generateBrackets() {
		return Math.random() > 0.8d;
	}

	@NotNull
	private String generateOperation() {
		String result;

		final int operationId = (int)(Math.random() * 4d);
		switch (operationId) {
			case 0:
				result = "+";
				break;
			case 1:
				result = "-";
				break;
			case 2:
				result = "*";
				break;
			case 3:
				result = "/";
				break;
/*				case 4:
				operation = "^";
				break;*/
			default:
				throw new UnsupportedOperationException("Check!");
		}

		return result;
	}

	private String generateFunction() {
		String result;
		final int operationId = (int)(Math.random() * 8d);
		switch (operationId) {
			case 0:
				result = "sin";
				break;
			case 1:
				result = "cos";
				break;
			case 2:
				result = "√";
				break;
			case 3:
				result = "ln";
				break;
/*				case 4:
				operation = "^";
				break;*/
			default:
				result = null;
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
