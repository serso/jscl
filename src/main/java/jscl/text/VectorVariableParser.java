package jscl.text;

import jscl.math.JSCLVector;
import jscl.math.Variable;
import jscl.math.VectorVariable;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

public class VectorVariableParser implements Parser<Variable> {
    public static final Parser<Variable> parser=new VectorVariableParser();

    private VectorVariableParser() {}

    public Variable parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        JSCLVector v;
        try {
            v=(JSCLVector)VectorParser.parser.parse(string, position);
        } catch (ParseException e) {
            throw e;
        }
        return new VectorVariable(v);
    }
}
