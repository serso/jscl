package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JsclVector;
import jscl.math.Variable;

public class Substitute extends Operator {
    public Substitute(Generic expression, Generic variable, Generic value) {
        super("subst",new Generic[] {expression,variable,value});
    }

    public Generic compute() {
        if(parameters[1] instanceof JsclVector && parameters[2] instanceof JsclVector) {
            Generic a= parameters[0];
            Variable variable[]=variables(parameters[1]);
            Generic s[]=((JsclVector) parameters[2]).elements();
            for(int i=0;i<variable.length;i++) a=a.substitute(variable[i],s[i]);
            return a;
        } else {
            Variable variable= parameters[1].variableValue();
            return parameters[0].substitute(variable, parameters[2]);
        }
    }

    public Operator transmute() {
        Generic p[]=new Generic[] {null,GenericVariable.content(parameters[1]),GenericVariable.content(parameters[2])};
        if(p[1] instanceof JsclVector && p[2] instanceof JsclVector) {
            return new Substitute(parameters[0],p[1],p[2]);
        }
        return this;
    }

    public Generic expand() {
        return compute();
    }

    public Variable newInstance() {
        return new Substitute(null,null,null);
    }
}
