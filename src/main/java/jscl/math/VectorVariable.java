package jscl.math;

import org.jetbrains.annotations.NotNull;

public class VectorVariable extends GenericVariable {
    public VectorVariable(Generic generic) {
        super(generic);
    }

    @NotNull
    public Variable newInstance() {
        return new VectorVariable(null);
    }
}
