package jscl.text;

import java.util.ArrayList;
import java.util.List;
import jscl.math.Generic;
import jscl.util.ArrayUtils;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class ParameterList implements Parser {
    public static final Parser parser=new ParameterList();

    private ParameterList() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        List l=new ArrayList();
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='(') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            Generic a=(Generic)ExpressionParser.parser.parse(string, position);
            l.add(a);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        while(true) {
            try {
                Generic a=(Generic)CommaAndExpression.parser.parse(string, position);
                l.add(a);
            } catch (ParseException e) {
                break;
            }
        }
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())==')') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        return (Generic[])ArrayUtils.toArray(l,new Generic[l.size()]);
    }
}
