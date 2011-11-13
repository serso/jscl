package jscl.text;

import java.util.ArrayList;
import java.util.List;
import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

public class VectorParser implements Parser {
    public static final Parser parser=new VectorParser();

    private VectorParser() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        List l=new ArrayList();
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='{') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            Generic a=(Generic)ExpressionParser.parser.parse(string, position, depth);
            l.add(a);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        while(true) {
            try {
                Generic a=(Generic)CommaAndExpression.parser.parse(string, position, depth);
                l.add(a);
            } catch (ParseException e) {
                break;
            }
        }
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='}') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        Generic element[]=(Generic[])ArrayUtils.toArray(l,new Generic[l.size()]);
        return new JsclVector(element);
    }
}
