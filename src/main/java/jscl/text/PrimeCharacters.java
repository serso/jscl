package jscl.text;

import org.jetbrains.annotations.NotNull;

public class PrimeCharacters implements Parser<Integer> {
    public static final Parser<Integer> parser=new PrimeCharacters();

    private PrimeCharacters() {}

    public Integer parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        int c;
        ParserUtils.skipWhitespaces(expression, position);
        if(position.intValue()< expression.length() && expression.charAt(position.intValue())=='\'') {
            expression.charAt(position.intValue());
			position.increment();
            c=1;
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        while(position.intValue()< expression.length() && expression.charAt(position.intValue())=='\'') {
            expression.charAt(position.intValue());
			position.increment();
            c++;
        }
        return c;
    }
}
