package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Root;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

public class RootParser implements Parser {
    public static final Parser parser=new RootParser();

    private RootParser() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        String name;
        Generic subscript;
        Generic a[];
        try {
            name=(String)Identifier.parser.parse(string, position);
            if(name.compareTo("root")==0);
            else {
                position.setValue(pos0);
                throw new ParseException();
            }
        } catch (ParseException e) {
            throw e;
        }
        try {
            subscript=(Generic)Subscript.parser.parse(string, position);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        try {
            a=(Generic[])ParameterList.parser.parse(string, position);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        return new Root(a,subscript);
    }
}
