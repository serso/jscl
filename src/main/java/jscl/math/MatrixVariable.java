package jscl.math;

import org.jetbrains.annotations.NotNull;

public class MatrixVariable extends GenericVariable {
        public MatrixVariable(Generic generic) {
                super(generic);
        }

        @NotNull
		public Variable newInstance() {
                return new MatrixVariable(null);
        }
}
