package jscl.text;

import jscl.math.Generic;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
public class PostfixFunctionParser implements Parser<PostfixFunctionParser.Result> {

    @Nonnull
    private final String postfixFunctionName;

    protected PostfixFunctionParser(@Nonnull String postfixFunctionName) {
        this.postfixFunctionName = postfixFunctionName;
    }

    @Nonnull
    public Result parse(@Nonnull Parameters p, Generic previousSumElement) throws ParseException {
        int pos0 = p.getPosition().intValue();

        final boolean postfixFunction;

        ParserUtils.skipWhitespaces(p);

        if (p.getPosition().intValue() < p.getExpression().length() && p.getExpression().startsWith(postfixFunctionName, p.getPosition().intValue())) {
            p.getPosition().add(postfixFunctionName.length());
            postfixFunction = true;
        } else {
            p.getPosition().setValue(pos0);
            postfixFunction = false;
        }

        return new Result(postfixFunctionName, postfixFunction);
    }

    public static class Result {

        @Nonnull
        private final String postfixFunctionName;

        private final boolean postfixFunction;

        public Result(@Nonnull String postfixFunctionName, boolean postfixFunction) {
            this.postfixFunctionName = postfixFunctionName;
            this.postfixFunction = postfixFunction;
        }

        public boolean isPostfixFunction() {
            return postfixFunction;
        }

        @Nonnull
        public String getPostfixFunctionName() {
            return postfixFunctionName;
        }
    }
}
