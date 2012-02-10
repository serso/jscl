package jscl2.math.numeric.matrix;

import jscl2.math.numeric.NumericNumber;
import org.jetbrains.annotations.NotNull;

/**
* User: serso
* Date: 2/10/12
* Time: 12:53 PM
*/
class Entry {

	private final int pos;

	@NotNull
	private final NumericNumber value;

	Entry(int pos, @NotNull NumericNumber value) {
		this.pos = pos;
		this.value = value;
	}

	public int getPos() {
		return pos;
	}

	@NotNull
	public NumericNumber getValue() {
		return value;
	}
}
