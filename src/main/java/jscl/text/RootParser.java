package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Function;
import jscl.math.function.Root;
import org.jetbrains.annotations.NotNull;

public class RootParser implements Parser<Function> {
    public static final Parser<Function> parser=new RootParser();

    private RootParser() {}

    public Function parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        String name;
        Generic subscript;
        Generic a[];
        try {
            name=(String)Identifier.parser.parse(expression, position, depth);
            if(name.compareTo("root")==0);
            else {
                position.setValue(pos0);
                throw new ParseException();
            }
        } catch (ParseException e) {
            throw e;
        }
        try {
            subscript=(Generic)Subscript.parser.parse(expression, position, depth);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        try {
            a=(Generic[])ParameterList.parser.parse(expression, position, depth);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        return new Root(a,subscript);
    }
}
