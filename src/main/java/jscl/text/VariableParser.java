package jscl.text;

import jscl.math.Variable;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

public class VariableParser implements Parser<Variable> {
    public static final Parser<Variable> parser=new VariableParser();

    private VariableParser() {}

    public Variable parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        Variable v;
        try {
            v=(Variable)OperatorParser.parser.parse(string, position);
        } catch (ParseException e) {
            try {
                v=(Variable)FunctionParser.parser.parse(string, position);
            } catch (ParseException e2) {
                try {
                    v=(Variable)ConstantParser.parser.parse(string, position);
                } catch (ParseException e3) {
                    throw e3;
                }
            }
        }
        return v;
    }
}
